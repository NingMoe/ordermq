package cn.donut.ordermq.worker;

import cn.donut.ordermq.entity.MqAttachments;
import cn.donut.ordermq.entity.MqInformation;
import cn.donut.ordermq.service.MqAttachmentsServiceProvider;
import cn.donut.ordermq.service.MqInformationServiceProvider;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.koolearn.clazz.model.UserProduct;
import com.koolearn.clazz.service.IUserProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiahao
 * @date 2018/5/15 9:33
 */
public class MsgReceiver implements MessageListener {

    private MqInformationServiceProvider mqInformationServiceProvider;
    private MqAttachmentsServiceProvider mqAttachmentsServiceProvider;
    private IUserProductService iUserProductService;

    private static final Logger log = LoggerFactory.getLogger(MsgReceiver.class);


    @Override
    public void onMessage(Message msg) {
        log.info("收到消息===>:{}", msg.toString());
        MqInformation mqInformation;
        String json;
        try {
            json = new String(msg.getBody(), Charset.defaultCharset());
            mqInformation = JSONObject.parseObject(json, MqInformation.class);
        } catch (JSONException e) {
            log.warn("消息格式不是JSON!", e);
            return;
        } catch (NullPointerException e) {
            log.warn("消息中不包含关键字段！或查询不到该信息！", e);
            return;
        }
        Date date = new Date();
        mqInformation.setIsPulish((byte) 0);
        mqInformation.setCreateTime(date);
        mqInformation.setIsDelete((byte) 0);
        mqInformation.setFailCount(0);
        HashMap<String, String> attachemts = mqInformation.getAttachments();
        mqInformationServiceProvider.insertMqInformation(mqInformation);
        log.info("MQ消息已保存！ID===>:{}", mqInformation.getId());
        if (attachemts != null) {
            for (Map.Entry<String, String> entry : attachemts.entrySet()) {
                MqAttachments mqAttachments = new MqAttachments();
                System.out.println(entry.getKey() + ":" + entry.getValue());
                mqAttachments.setTheKey(entry.getKey());
                mqAttachments.setTheValue(entry.getValue());
                mqAttachments.setId(mqInformation.getId());
                mqAttachments.setCreateTime(date);
                mqAttachments.setIsDelete((byte) 0);
                mqAttachmentsServiceProvider.insertMqAttachments(mqAttachments);
                log.info("附加消息已保存！ID===>:{}", mqAttachments.getId());
            }
        }

        UserProduct userProduct = new UserProduct();


        userProduct = iUserProductService.getById(466570);
        if (null == userProduct) {
            System.out.println();
        } else {

            System.out.println();
        }


    }

    public MqInformationServiceProvider getMqInformationServiceProvider() {
        return mqInformationServiceProvider;
    }

    public void setMqInformationServiceProvider(MqInformationServiceProvider mqInformationServiceProvider) {
        this.mqInformationServiceProvider = mqInformationServiceProvider;
    }

    public MqAttachmentsServiceProvider getMqAttachmentsServiceProvider() {
        return mqAttachmentsServiceProvider;
    }

    public void setMqAttachmentsServiceProvider(MqAttachmentsServiceProvider mqAttachmentsServiceProvider) {
        this.mqAttachmentsServiceProvider = mqAttachmentsServiceProvider;
    }

    public IUserProductService getiUserProductService() {
        return iUserProductService;
    }

    public void setiUserProductService(IUserProductService iUserProductService) {
        this.iUserProductService = iUserProductService;
    }
}
