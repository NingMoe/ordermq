/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Test
 * Author:   LiYuAn
 * Date:     2018/5/10 10:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author LiYuAn
 * @create 2018/5/10
 * @since 1.0.0
 */
@RequestMapping("/rabbit")
@Controller
public class RabbitTest {
    @Autowired
    private TopicSender topicSender;


    @RequestMapping(value = "/topicTest",method = RequestMethod.GET)
    @ResponseBody
    public void topicTest() {
        topicSender.send();
    }

}
