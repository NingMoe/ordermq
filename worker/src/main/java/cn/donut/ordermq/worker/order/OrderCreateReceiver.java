package cn.donut.ordermq.worker.order;


import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderService;
import cn.donut.ordermq.worker.MqUtil;
import cn.donut.retailm.entity.domain.DrOrderInfo;
import cn.donut.retailm.entity.model.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

/**
 * 创建订单监听
 *
 * @author wangjiahao
 *         〈一句话功能简述〉<br>
 *         〈订单创建-MQ消息接受处理〉
 * @author LiYuAn
 * @create 2018/6/28
 * @sice 1.0.0
 */
@Slf4j
public class OrderCreateReceiver implements MessageListener {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private MqRecordService mqRecordService;

    @Autowired
    private cn.donut.retailm.service.order.IOrderService iRetailmOrderService;

    @Autowired
    private MqUtil mqUtil;

    /**
     * 1.接受消息--->存库、实例化
     * 2.对信息进行业务处理
     * 3.分发
     *
     * @param msg
     */
    @Override
    public void onMessage(final Message msg) {


        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String json = new String(msg.getBody(), Charset.defaultCharset());
                log.info("收到消息：==>{}" + json);
                //转换并校验json格式
                MqOrderInfo orderInfo = mqUtil.Json2Order(json);
                //是否多纳订单
                boolean flag = iOrderService.checkProLine(orderInfo);
                if (orderInfo != null && flag) {
                    //保存
                    MqRecord mqRecord = mqUtil.saveMsg(json, "order.create");
                    if (mqRecord != null) {
                        try {
                            //保存订单
                            MqOrderInfo order = iOrderService.saveOrder(orderInfo);
                            if (order != null) {
                                //回写消息状态为实例化成功
                                mqRecord.setPersist((byte) 1);
                                mqRecordService.edit(mqRecord);
                                log.info("订单创建已完成！订单号：{}", order.getOrderNo());
                                //同步数据到分销员系统
                                editRetailm(order);

                            } else {
                                log.info("订单已存在！");
                            }
                        } catch (Exception e) {
                            log.error("插入订单和产品失败！", e);
                        }
                    }


                }
                // TODO: 2018/6/29 做出分发
                // TODO: 2018/6/29 分发记录
            }
        });


    }

    //回写分销系统状态
    private Boolean editRetailm(MqOrderInfo mqOrderInfo) {

        DrOrderInfo drOrderInfo = new DrOrderInfo();
        Map<String, Object> map = iRetailmOrderService.findOrderByTradeNo(mqOrderInfo.getOrderNo());

        if (null != map && map.containsKey("orderInfo")) {
            drOrderInfo = (DrOrderInfo) map.get("orderInfo");
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //待支付
            drOrderInfo.setStatus((byte) 0);
            drOrderInfo.setUpdateTime(new Date());

            Integer id = mqUtil.getRetailMemberId(mqOrderInfo);
            if (null != id) {
                drOrderInfo.setRetailMemberId(id);
            }

            return iRetailmOrderService.insertOrder(drOrderInfo) != null;
        } else {
            //没订单数据，就要新增了
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //分销员id
            drOrderInfo.setRetailMemberId(mqUtil.getRetailMemberId(mqOrderInfo));
            drOrderInfo.setUpdateTime(new Date());
            //待支付
            drOrderInfo.setStatus((byte) 0);
            drOrderInfo.setNetWorth(mqOrderInfo.getNetValue());
            drOrderInfo.setRealPrice(mqOrderInfo.getStrikePrice());
            drOrderInfo.setPayTime(mqOrderInfo.getPayTime());
            drOrderInfo.setOrderTime(mqOrderInfo.getOrderTime());
            drOrderInfo.setPrice(mqOrderInfo.getOriginalPrice());
            OrderModel orderModel = iRetailmOrderService.insertOrder(drOrderInfo);
            if (null != orderModel && null != orderModel.getId()) {
                return true;
            }
            return false;
        }

    }
}
