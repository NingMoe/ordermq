package cn.donut.ordermq.worker.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.nio.charset.Charset;

/**
 * 订单退课监听
 * @author wangjiahao
 * @date 2018/6/29 9:31
 */
@Slf4j
public class OrderRefundReceiver implements MessageListener {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void onMessage(final Message message) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                log.info("收到消息：==>{}" + message.toString());
                String json = new String(message.getBody(), Charset.defaultCharset());
                //TODO:具体处理
                System.out.println("json = " + json);
            }
        });
    }
}
