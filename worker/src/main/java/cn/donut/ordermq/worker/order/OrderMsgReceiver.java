package cn.donut.ordermq.worker.order;

import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

/**
 * 订单消息接收
 *
 * @author wangjiahao
 */
public class OrderMsgReceiver implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(OrderMsgReceiver.class);

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

    }
}
