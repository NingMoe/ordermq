package cn.donut.ordermq.worker;

import cn.donut.ordermq.entity.MqInformation;
import cn.donut.ordermq.service.MqAttachmentsServiceProvider;
import cn.donut.ordermq.service.MqInformationServiceProvider;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.koolearn.clazz.model.UserProduct;
import com.koolearn.clazz.service.IUserProductService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiahao
 * @date 2018/5/15 9:33
 */
public class MsgReceiver implements MessageListener {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private MqInformationServiceProvider mqInformationServiceProvider;

    @Autowired
    private MqAttachmentsServiceProvider mqAttachmentsServiceProvider;

    @Autowired
    private IUserProductService iUserProductService;

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
    public void onMessage(final Message msg) {

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                log.warn("Received the message===>:{}", msg.toString());
                MqInformation mqInformation;
                String json;
                MqInformation mq;
                try {
                    json = new String(msg.getBody(), Charset.defaultCharset());
                    mqInformation = JSONObject.parseObject(json, MqInformation.class);
                    log.warn("mqInformation Received!");
                } catch (JSONException e) {
                    log.warn("消息格式不是JSON!", e);
                    return;
                } catch (NullPointerException e) {
                    log.warn("消息中不包含关键字段！或查询不到该信息！", e);
                    return;
                }
                UserProduct userProduct = iUserProductService.getById(mqInformation.getPrimaryKey());
                Map<String, Object> jsonMap = new HashMap<String, Object>();
                if (null == userProduct) {
                    log.warn("没有查询到对应数据！primarykey:{}",mqInformation.getPrimaryKey());
                    jsonMap.put("userProduct", "");
                } else {
                    log.warn("userProductId:{}", userProduct.toString());
                    if ((userProduct.getProductLine() == 49 || userProduct.getProductLine() == 58) && AddressInfo.map.containsKey(userProduct.getProductId())) {
                        //数据存库
                        mq = mqInformationServiceProvider.insertMqInformation(mqInformation);
                        jsonMap = this.Object2Json(userProduct);
                        log.warn("jsonMap:{}", jsonMap.toString());
                        log.warn("Url:{}", AddressInfo.map.get(userProduct.getProductId()));
                        String content = HttpClientUtil.doPost(AddressInfo.map.get(userProduct.getProductId()), jsonMap);
                        log.warn("httpClient返回消息", content);
                        if (StringUtils.isNotEmpty(content) && content.contains("成功")) {
                            //回写推送字段
                            mqInformation.setId(mq.getId());
                            mqInformation.setIsPulish((byte) 1);
                            mqInformation.setPushTime(new Date());
                            mqInformation.setUpdateTime(new Date());
                            mqInformationServiceProvider.updateMqInformation(mqInformation);
                            System.out.println("pushed");
                        }
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
        });

    }


}
