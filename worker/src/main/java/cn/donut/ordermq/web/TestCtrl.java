package cn.donut.ordermq.web;

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
import org.springframework.stereotype.Controller;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiahao
 * @date 2018/5/14 18:32
 */
@Controller
public class TestCtrl {
//
    private static final Logger log = LoggerFactory.getLogger(TestCtrl.class);
//
    @Autowired
    private MqInformationServiceProvider mqInformationServiceProvider;
    @Autowired
    private MqAttachmentsServiceProvider mqAttachmentsServiceProvider;


}
