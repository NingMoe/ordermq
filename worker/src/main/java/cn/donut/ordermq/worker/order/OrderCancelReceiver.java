package cn.donut.ordermq.worker.order;

import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderProductService;
import cn.donut.ordermq.service.order.IOrderService;
import cn.donut.ordermq.worker.MqUtil;
import cn.donut.retailm.entity.domain.DrOrderInfo;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParseException;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.model.order.basic.OrderProductBasicInfo;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

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
                log.info("收到消息：==>{}" + json);
                //转换并校验json格式
                MqOrderInfo orderInfo = mqUtil.Json2Order(json);
                //是否多纳订单
                boolean flag = iOrderService.checkProLine(orderInfo);
                if (orderInfo != null && flag) {
                    //保存
                    MqRecord mqRecord = mqUtil.saveMsg(json, "order.cancel");
                    if (mqRecord != null) {

                        //更新订单和相关产品数据库
                        MqOrderInfo order = null;
                        try {
                            order = updateOrder(orderInfo);
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error("修改产品信息失败！");
                        }
                        if (order != null) {
                            //回写消息状态
                            mqRecord.setPersist((byte) 1);
                            mqRecordService.edit(mqRecord);
                            log.info("订单取消已更新！订单号：{}", order.getOrderNo());
                        } else {
                            log.info("订单已存在！");
                        }
                    }
                }
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


}
