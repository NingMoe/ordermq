package cn.donut.ordermq.worker.order;


import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderService;
import cn.donut.ordermq.worker.MqUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.nio.charset.Charset;
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
                Map<String, Object> map = iOrderService.checkProLine(orderInfo);
                Boolean flag = false;
                if (orderInfo != null && flag) {
                    //保存
                    MqRecord mqRecord = mqUtil.saveMsg(json, "order.create");
                    if (mqRecord != null) {
                        try {
                            //保存订单和产品
                            MqOrderInfo order = iOrderService.saveOrder(orderInfo);
                            if (order != null) {
                                //回写消息状态为实例化成功
                                mqRecord.setPersist((byte) 1);
                                mqRecordService.edit(mqRecord);
                                pushAopCreate(map, order);
                                log.info("订单创建已完成！订单号：{}", order.getOrderNo());
                            } else {
                                log.info("订单已存在！");
                            }
                        } catch (Exception e) {
                            log.error("插入订单和产品失败！", e);
                        }
                    }


                }
                sendMsg();
                // TODO: 2018/6/29 做出分发
                // TODO: 2018/6/29 分发记录
            }
        });

    }

    public Map<String, Object> pushAopCreate(Map<String, Object> map, MqOrderInfo order) {
        System.out.println("执行下单业务处理--------------");
        map.put("order", order);
        return map;
    }

    //aop测试方法
    public void sendMsg() {
        System.out.println("AOP通知");
    }
}
