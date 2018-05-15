package cn.donut.ordermq.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author wangjiahao
 * @date 2018/5/15 9:33
 */
@Component
public class MsgReceiver implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("message.toString() = " + message.toString());
    }
}
