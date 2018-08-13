package cn.donut.ordermq.worker.order;

import cn.donut.ordermq.dto.OrderBasicInfoDto;
import cn.donut.ordermq.entity.MqPushFailure;
import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.service.MqPushFailureService;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderService;
import cn.donut.ordermq.worker.Global;
import cn.donut.ordermq.worker.HttpClientUtil;
import cn.donut.ordermq.worker.MqUtil;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 取消订单监听
 *
 * @author wangjiahao
 * @date 2018/6/29 9:31
 */
@Slf4j
public class OrderCancelReceiver implements MessageListener {


    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private MqRecordService mqRecordService;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IOrderBasicInfoService iOrderBasicInfoService;

    @Autowired
    private MqUtil mqUtil;

    @Autowired
    private Global global;

    @Autowired
    private MqPushFailureService mqPushFailureService;
//    {
//        "orderNo": "订单号",
//        "productIdList": [产品id1],
//        "userId": 下单用户id
//    }


    @Override
    public void onMessage(final Message message) {

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String json = new String(message.getBody(), Charset.defaultCharset());
                System.out.println("取消订单收到消息：==>{}" + json);
                //转换并校验json格式
                MqOrderInfo orderInfo = mqUtil.jsonToOrder(json);
                //是否多纳订单
                Map<String, Object> map = iOrderService.checkProLine(orderInfo);
                Boolean flag = false;
                if (null != map && map.containsKey("flag")) {
                    flag = (Boolean) map.get("flag");
                }
                if (orderInfo != null && flag) {
                    //保存
                    MqRecord mqRecord = mqUtil.saveMsg(json, "order.cancel");
                    if (mqRecord != null) {

                        //更新订单和相关产品数据库
                        MqOrderInfo order = null;
                        try {
                            order = updateOrder(orderInfo);
                            if (order != null) {
                                //回写消息状态
                                mqRecord.setPersist((byte) 1);
                                mqRecordService.edit(mqRecord);
                                //推送到分销
                                try {
                                    pushAop(map, order);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                log.warn("订单取消已更新！订单号：{}", order.getOrderNo());

                                log.error("OrderCancelReceiver推送node判断，包含infoList则进入循环");
                                //推送node
                                if( map.containsKey("infoList")){
                                    log.error("进入infoList==推送node start==========");
                                    List<OrderBasicInfoDto> infoList = (List<OrderBasicInfoDto>) map.get("infoList");
                                    log.error("infoList=="+infoList);
                                    if(infoList!=null&&infoList.size()>0){
                                        for (int i=0;i<infoList.size();i++){
                                            String url=infoList.get(i).getUrl();
                                            Map<String,Object> params=new HashMap<String, Object>();
                                            params.put("data",infoList.get(i));
                                            String content = HttpClientUtil.doPost(url, params);
                                            log.warn("httpClient返回消息", content);
                                            //发送失败
                                            if (! (StringUtils.isNotEmpty(content) && content.contains("成功"))){
                                                MqPushFailure mqPushFailure = new MqPushFailure();
                                                mqPushFailure.setPushTarget(url);
                                                mqPushFailure.setMessage(json);
                                                mqPushFailure.setOriginalRoute("order.cancel");
                                                log.error("mqPushFailure=="+mqPushFailure);
                                                try {
                                                    mqPushFailureService.insert(mqPushFailure);
                                                    log.error("进入infoList==推送node end==========");
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                    log.error("消息插入到mqPushFailure数据库失败");
                                                    log.error("json:"+json);
                                                }
                                            }
                                        }
                                    }
                                }
                                log.error("循环结束==========");
                            } else {
                                log.error("订单取消更新数据库失败！，请检查原因");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("修改产品信息失败！");
                        }
                    }
                }
                global.sendMsg();
            }
            // TODO: 2018/6/29 做出分发
            // TODO: 2018/6/29 分发记录
        });
    }


    /**
     * 从订单中心拿到订单具体信息，更新我方数据库数据
     *
     * @param orderInfo
     * @return MqOrderInfo
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqOrderInfo updateOrder(MqOrderInfo orderInfo) throws Exception {

        //从鲨鱼拿到订单并复制
        OrderBasicInfo info = iOrderBasicInfoService.findOrderBasicInfoByOrderNo(orderInfo.getOrderNo(), true);
        MqOrderInfo one = iOrderService.findOneByOrderNo(info.getOrderNo());

        BeanUtils.copyProperties(info, orderInfo);
        MqOrderInfo order;
        //如果本地没有数据，执行新增，外层已执行产品线校验
        if (null != one) {
            orderInfo.setId(one.getId());
            order = iOrderService.editOrder(orderInfo);
            if (null != order) {
                try {
                    List<MqOrderProduct> products = mqUtil.updateProducts(info);
                    if (products != null && products.size() > 0) {
                        order.setMqOrderProducts(products);
                    }
                    return order;
                } catch (Exception e) {
                    log.error("修改产品信息失败！", e);
                    return null;
                }
            } else {
                log.error("修改产品信息失败！");
                return null;
            }
        } else {//新增
            orderInfo.setId(null);
            //插入订单和产品
            order = iOrderService.saveOrder(orderInfo);
            return order;
        }


    }



    public Map<String, Object> pushAop(Map<String, Object> map, MqOrderInfo order) throws Exception {
        System.out.println("执行取消业务处理--------------");
        mqUtil.pushOrderToRetailm(order);
        map.put("order", order);
        return map;
    }
}
