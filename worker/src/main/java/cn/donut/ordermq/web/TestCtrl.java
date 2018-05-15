package cn.donut.ordermq.web;

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


    @RequestMapping(method = RequestMethod.GET,value = "hi")
    @ResponseBody
    public String hi(){
        amqpTemplate.convertAndSend("sharks.data.change","sharks.data.change.*.*","hi");
        return "ok";
    }

}
