package cn.donut.ordermq.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangjiahao
 * @date 2018/5/14 18:32
 */
@Controller
public class TestCtrl {
//
    private static final Logger log = LoggerFactory.getLogger(TestCtrl.class);

    @RequestMapping("/health")
    @ResponseBody
    public String health(){
        return "{STATUS:UP!}";
    }

}
