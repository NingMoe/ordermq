package order.product;

import cn.donut.ordermq.entity.MqOrderPush;
import cn.donut.ordermq.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 测试用
 *
 * @author luoxuzheng
 * @create 2018-08-13 17:34
 **/
@Controller
@RequestMapping("ordermq/test")
public class TestController {

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping(value = "/insetPush", method = RequestMethod.GET)
    @ResponseBody
    public String test(Integer productId) {
        MqOrderPush push = new MqOrderPush();
        push.setProductid(productId);
        push.setUrl("http://doabc.leanapp.cn/api/yc/buy");
        push.setIsDelete(0);
        push.setRemark("测试数据");
        push.setCreateAt(new Date());
        push.setUpdateAt(new Date());
        System.out.println(push.getUrl());
        iOrderService.test(push);
        return "ok";
    }
}
