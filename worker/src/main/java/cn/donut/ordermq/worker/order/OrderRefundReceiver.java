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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单退课监听
 *
 * @author wangjiahao
 * @date 2018/6/29 9:31
 */
@Slf4j
public class OrderRefundReceiver implements MessageListener {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private MqRecordService mqRecordService;

    @Autowired
    private IOrderBasicInfoService iOrderBasicInfoService;

    @Autowired
    private IOrderProductService iOrderProductService;

    @Autowired
    private cn.donut.retailm.service.order.IOrderService iRetailmOrderService;

    @Autowired
    private MqUtil mqUtil;

    @Override
    public void onMessage(final Message msg) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        String json = new String(msg.getBody(), Charset.defaultCharset());
                        log.info("收到消息：==>{}" + json);
                        //转换
                        MqOrderInfo orderInfo = mqUtil.Json2Order(json);
                        //是否多纳订单
                        boolean flag = iOrderService.checkProLine(orderInfo);
                        if (orderInfo != null && flag) {
                            //保存
                            MqRecord mqRecord = saveMsg(json);
                            if (mqRecord != null) {
                                try {
                                    MqOrderInfo order = updateData(orderInfo);
                                    if (order != null) {
                                        //回写消息状态
                                        mqRecord.setPersist((byte) 1);
                                        mqRecordService.edit(mqRecord);
                                        if (editRetailm(order)) {
                                            log.info("分销系统订单回写成功！订单号：{}", order.getOrderNo());
                                        } else {
                                            log.info("分销系统订单回写失败！订单号：{}", order.getOrderNo());
                                        }
                                        log.info("订单更新已完成！订单号：{}", order.getOrderNo());
                                    } else {
                                        log.info("订单更新失败！");
                                    }
                                } catch (Exception e) {
                                    log.error("更新订单和产品失败！", e);
                                }
                            }
                        }
                        // TODO: 2018/6/29 做出分发
                        // TODO: 2018/6/29 分发记录
                    }
                });
            }
        });
    }

    /**
     * 接收消息，将消息存入数据库，转换成订单对象并返回
     *
     * @param json
     * @return MqOrderInfo
     */
    private MqRecord saveMsg(String json) {
        MqRecord record = new MqRecord();
        record.setJsonContent(json);
        record.setCreateTime(new Date());
        record.setPersist((byte) 0);
        record.setRoutingKey("order.refund");
        return mqRecordService.insert(record);
    }


    /**
     * 业务处理:根据订单号查询最新实体，并更新本地数据
     *
     * @param orderInfo
     * @return MqOrderInfo
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqOrderInfo updateData(MqOrderInfo orderInfo) throws Exception {

        //从鲨鱼拿到订单并复制
        OrderBasicInfo info = iOrderBasicInfoService.findOrderBasicInfoByOrderNo(orderInfo.getOrderNo(), true);
        //查询本地数据
        MqOrderInfo mqOrderInfo = iOrderService.findOneByOrderNo(orderInfo.getOrderNo());
        //订单不为空时，要判断订单状态是否为支付成功
        MqOrderInfo order;
        if (null != mqOrderInfo && mqOrderInfo.getStatus() == 1) {
            Integer tempId = mqOrderInfo.getId();
            BeanUtils.copyProperties(mqOrderInfo, info);
            mqOrderInfo.setId(tempId);
            //更新数据
            order = iOrderService.editOrder(mqOrderInfo);
            if (null != order) {
                List<MqOrderProduct> list = iOrderProductService.findProductsByOrderNo(order.getOrderNo());
                //TODO 是否要判空
                for (MqOrderProduct product : list) {
                    product.setProductstatus(5);
                    iOrderProductService.editOrderProduct(product);
                }
                order.setMqOrderProducts(list);
                return order;
            } else {
                return order;
            }

        } else {

            //订单为空时，应去鲨鱼查询订单是否存在，因外层已做产品线校验，直接新增就行
            BeanUtils.copyProperties(mqOrderInfo, info);
            mqOrderInfo.setId(null);
            order = iOrderService.saveOrder(mqOrderInfo);
            return order;
        }

    }

    //回写状态
    private Boolean editRetailm(MqOrderInfo mqOrderInfo) {
        DrOrderInfo drOrderInfo = new DrOrderInfo();
        Map<String, Object> map = iRetailmOrderService.findOrderByTradeNo(mqOrderInfo.getOrderNo());
        if (map != null && map.containsKey("orderInfo")) {
            drOrderInfo = (DrOrderInfo) map.get("orderInfo");
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //已退款
            drOrderInfo.setStatus((byte) 2);
            drOrderInfo.setUpdateTime(new Date());
            return iRetailmOrderService.editOrder(drOrderInfo);
        } else {
            return false;
        }

    }
}
