package cn.donut.ordermq.worker.order;


import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

/**
 * 创建订单监听
 * @author wangjiahao
 */
@Slf4j
public class OrderCreateReceiver implements MessageListener {

    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private MqRecordService mqRecordService;

    @Override
    public void onMessage(Message msg) {
        log.info("收到消息：==>{}" + msg.toString());
        String json = new String(msg.getBody(), Charset.defaultCharset());
        //TODO:具体处理
        System.out.println("json = " + json);
        MqOrderInfo one = iOrderService.findOneByOrderId(1);
//        MqRecord record = mqRecordService.findOneByid(1);

//        1.接收消息-->包括解析，存入消息数据库

//        2.拿到解析后的数据，处理
//        3.分发
//        4.做出记录

    }
}
