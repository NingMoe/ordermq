package cn.donut.ordermq.consumer;

import cn.donut.ordermq.entity.MqAttachments;
import cn.donut.ordermq.entity.MqInformation;
import cn.donut.ordermq.service.MqAttachmentsServiceProvider;
import cn.donut.ordermq.service.MqInformationServiceProvider;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiahao
 * @date 2018/5/15 9:33
 */
@Component
public class MsgReceiver implements MessageListener {


    @Autowired
    private MqInformationServiceProvider mqInformationServiceProvider;

    @Autowired
    private MqAttachmentsServiceProvider mqAttachmentsServiceProvider;


    private byte isNotPush = 0;

    private final static Logger log = LoggerFactory.getLogger(MsgReceiver.class);

    @Override
    public void onMessage(Message msg) {
//        System.out.println("message.toString() = " + message.toString());

        log.info("收到消息===>:{}", msg);
        MqInformation mqInformation = new MqInformation();
        String json;

        System.out.println(msg.getBody().toString());
        try {
            json = new String(msg.getBody(), Charset.defaultCharset());
            mqInformation = JSONObject.parseObject(json, MqInformation.class);

        } catch (JSONException e) {
            log.warn("消息格式不是JSON!", e);
        } catch (NullPointerException e) {
            log.warn("消息中不包含关键字段！或查询不到该信息！", e);
        }
        mqInformation.setIsPulish(isNotPush);
        mqInformation.setCreateTime(new Date());
        HashMap<String, String> attachemts = mqInformation.getAttachments();
        this.mqInformationServiceProvider.insertMqInformation(mqInformation);
        log.info("MQ消息已保存！ID===>:{}", mqInformation.getId());
        if (attachemts != null) {
            for (Map.Entry<String, String> entry : attachemts.entrySet()) {
                MqAttachments mqAttachments = new MqAttachments();
                System.out.println(entry.getKey() + ":" + entry.getValue());
                mqAttachments.setTheKey(entry.getKey());
                mqAttachments.setTheValue(entry.getValue());
                mqAttachments.setId(mqInformation.getId());
                mqAttachmentsServiceProvider.insertMqAttachments(mqAttachments);
                log.info("附加消息已保存！ID===>:{}", mqAttachments.getId());
            }
        }

    }
}
