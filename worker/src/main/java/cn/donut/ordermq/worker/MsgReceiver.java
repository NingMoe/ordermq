package cn.donut.ordermq.worker;

import cn.donut.ordermq.entity.MqAttachments;
import cn.donut.ordermq.entity.MqInformation;
import cn.donut.ordermq.service.MqAttachmentsServiceProvider;
import cn.donut.ordermq.service.MqInformationServiceProvider;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.koolearn.clazz.model.UserProduct;
import com.koolearn.clazz.service.IUserProductService;
import com.koolearn.common.config.Configuration;
import com.koolearn.common.config.impl.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
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
    //测试地址
    private final static String url = "https://doabc.leanapp.cn/api/v1/web/yc/apply/status";
    //    正式地址
    //    private final static String url = "https://doabc.leanapp.cn/api/v1/web/yc/apply/status";

    private static final Logger log = LoggerFactory.getLogger(MsgReceiver.class);

    /**
     * json格式{
     * "dataType": "数据类型",// 变更更的数据类型
     * "changeType": 1, // 变更更类型 1.新增 2.修改 3.删除
     * "primaryKey": xxxx, //变更更数据主键
     * "productLine":xxx,//数据对应的产品线id
     * "attachments": { //附加信息
     * "xxxx": "xxx" // 附加信息
     * }
     * }
     *
     * @param msg
     */
    @Override
    public void onMessage(Message msg) {
        log.info("Received the message===>:{}", msg.toString());
        MqInformation mqInformation;
        String json;
        try {
            json = new String(msg.getBody(), Charset.defaultCharset());
            mqInformation = JSONObject.parseObject(json, MqInformation.class);
            mqInformation = this.saveMqInfor(mqInformation);
        } catch (JSONException e) {
            log.warn("消息格式不是JSON!", e);
            return;
        } catch (NullPointerException e) {
            log.warn("消息中不包含关键字段！或查询不到该信息！", e);
            return;
        }


        //附加信息部分
        HashMap<String, String> attachemts = mqInformation.getAttachments();
        if (attachemts != null) {
            this.saveAttachments(mqInformation, attachemts);
        }

        UserProduct userProduct = new UserProduct();
        userProduct = iUserProductService.getById(mqInformation.getPrimaryKey());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if (null == userProduct) {
            log.warn("没有查询到对应数据！");
            jsonMap.put("userProduct", "");
        } else {
            log.info("userProduct：{}", userProduct);
            if (userProduct.getProductLine() == 49 || userProduct.getProductLine() == 58) {
                jsonMap = this.Object2Json(userProduct);
                String content = HttpClientUtil.doPost(url, jsonMap);
                log.info("httpClient返回消息", content);
                System.out.println("httpClient返回消息" + content);
                if (StringUtils.isNotEmpty(content)) {
                    //回写推送字段
                    mqInformation.setIsPulish((byte) 1);
                    mqInformation.setPushTime(new Date());
                    mqInformation.setUpdateTime(new Date());
                    this.mqInformationServiceProvider.updateMqInformation(mqInformation);
                    System.out.println("pushed");
                }
            } else {
                log.info("不符合条件的ProductLine！");
            }

        }


    }

    //UserProduct转为Map<String,Object>
    private Map<String, Object> Object2Json(UserProduct userProduct) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if (null == userProduct) {
            jsonMap.put("userProduct", "");
            return jsonMap;
        }

        jsonMap.put("id", userProduct.getId());
        jsonMap.put("userId", userProduct.getUserId());
        jsonMap.put("orderNo", userProduct.getOrderNo());
        jsonMap.put("productId", userProduct.getProductId());
        jsonMap.put("status", userProduct.getStatus());
        jsonMap.put("failureCount", userProduct.getFailureCount());
        jsonMap.put("buyTime", userProduct.getBuyTime() != null ? DateUtil.convertTimeToString(userProduct.getBuyTime()) : "");
        jsonMap.put("overTime", userProduct.getOverTime() != null ? DateUtil.convertTimeToString(userProduct.getOverTime()) : "");
        jsonMap.put("freezeTime", userProduct.getFreezeTime() != null ? DateUtil.convertTimeToString(userProduct.getFreezeTime()) : "");
        jsonMap.put("createTime", userProduct.getCreateTime() != null ? DateUtil.convertTimeToString(userProduct.getCreateTime()) : "");

        return jsonMap;

    }

    //保存json中mq消息实体
    private MqInformation saveMqInfor(MqInformation mqInformation) {

        Date date = new Date();
        mqInformation.setIsPulish((byte) 0);
        mqInformation.setCreateTime(date);
        mqInformation.setIsDelete((byte) 0);
        mqInformation.setFailCount(0);
        int id = mqInformationServiceProvider.insertMqInformation(mqInformation);
        System.out.println("id=" + id);
        log.info("Message has been saved！ID===>:{}", mqInformation.getId());
        System.out.println("Message has been saved！ID===>:{}" + mqInformation.getId());

        return mqInformation;
    }

    //保存附加信息
    private void saveAttachments(MqInformation mqInformation, HashMap<String, String> attachemts) {
        Date date = new Date();
        for (Map.Entry<String, String> entry : attachemts.entrySet()) {
            MqAttachments mqAttachments = new MqAttachments();
            System.out.println(entry.getKey() + ":" + entry.getValue());
            mqAttachments.setTheKey(entry.getKey());
            mqAttachments.setTheValue(entry.getValue());
            mqAttachments.setId(mqInformation.getId());
            mqAttachments.setCreateTime(date);
            mqAttachments.setIsDelete((byte) 0);
            mqAttachmentsServiceProvider.insertMqAttachments(mqAttachments);
            log.info("Attachments has been saved！ID===>:{}", mqAttachments.getId());
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
