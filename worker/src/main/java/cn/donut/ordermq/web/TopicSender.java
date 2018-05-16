/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TopicSender
 * Author:   LiYuAn
 * Date:     2018/5/10 10:08
 * Description: TopicSender
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈TopicSender 〉
 *
 * @author LiYuAn
 * @create 2018/5/10
 * @since 1.0.0
 */
@Component
public class TopicSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {

        for (int i = 0; i < 3; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dataType", i);
            jsonObject.put("changeType", 1);
            jsonObject.put("primaryKey", i + i);
            jsonObject.put("productLine", i + i);
            HashMap<String, String> hashMap = Maps.newHashMap();
            hashMap.put("附加", "1");
            jsonObject.put("primaryKey", i + i);
            jsonObject.put("attachments", hashMap);
            //@param firest:queue second:routing key third:message
            this.rabbitTemplate.convertAndSend("sharks.data.change", "sharks.data.change.userProduct.DONUT", jsonObject.toString());
        }

    }

}
