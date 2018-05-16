package cn.donut.ordermq.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangjiahao
 * @date 2018/5/14 18:32
 */
@Controller
public class TestCtrl {

    @Autowired
    private AmqpTemplate amqpTemplate;
//
//    @Autowired
//    private TestService testService;

    @RequestMapping(method = RequestMethod.GET,value = "hi")
    @ResponseBody
    public void hi(){
//        amqpTemplate.convertAndSend("sharks.data.change","sharks.data.change.*.*","hi");
//        return testService.test();

        for (int i = 0; i < 3; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dataType", i);
            jsonObject.put("changeType", 1);
            jsonObject.put("primaryKey", i + i);
            jsonObject.put("productLine", i + i);
//            HashMap<String, String> hashMap = new HashMap<String, String>();
//            hashMap.put("附加", "1");
            jsonObject.put("primaryKey", i + i);
//            jsonObject.put("attachments", hashMap);
            //@param firest:queue second:routing key third:message
            this.amqpTemplate.convertAndSend("sharks.data.change-donut", "sharks.data.change.userProduct.*", jsonObject.toString());
        }
    }

}
