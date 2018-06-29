package cn.donut.ordermq.worker.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.nio.charset.Charset;

/**
 * 取消订单监听
 * @author wangjiahao
 * @date 2018/6/29 9:31
 */
@Slf4j
public class OrderCancelReceiver implements MessageListener {

    @Override
    public void onMessage(Message message) {
        log.info("收到消息：==>{}" + message.toString());
        String json = new String(message.getBody(), Charset.defaultCharset());
        //TODO:具体处理
        System.out.println("json = " + json);
    }
}
