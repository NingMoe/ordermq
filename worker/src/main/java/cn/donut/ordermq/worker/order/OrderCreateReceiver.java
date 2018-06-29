package cn.donut.ordermq.worker.order;


import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderProductService;
import cn.donut.ordermq.service.order.IOrderService;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.model.order.basic.OrderProductBasicInfo;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈订单创建-MQ消息接受处理〉
 *
 * @author LiYuAn
 * @create 2018/6/28
 * @sice 1.0.0
 */

public class OrderCreateReceiver implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(OrderCreateReceiver.class);

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private MqRecordService mqRecordService;

    @Autowired
    private IOrderBasicInfoService iOrderBasicInfoService;

    @Autowired
    private IOrderProductService iOrderProductService;

    /**
     * 1.接受消息--->存库、实例化
     * 2.对信息进行业务处理
     * 3.分发
     *
     * @param msg
     */
    @Override
    public void onMessage(Message msg) {
        //
        log.info("收到消息：==>{}" + msg.toString());
        String json = "";
        JSONObject jsStr;
        //TODO:具体处理
        try {
            json = new String(msg.getBody(), Charset.defaultCharset());
            jsStr = JSONObject.parseObject(json);
            log.warn("mqInformation saved!");
        } catch (JSONException e) {
            log.warn("消息格式不是JSON!", e);
            return;
        } catch (NullPointerException e) {
            log.warn("消息中不包含关键字段！或查询不到该信息！", e);
            return;
        }
        //入库解析后的json
        MqRecord mqRecord = new MqRecord();
        mqRecord.setJsonContent(json);
        //未持久化
        mqRecord.setPersist((byte) 0);
        mqRecord.setRoutingKey("order.create");
        mqRecord = mqRecordService.insert(mqRecord);
        if (null != mqRecord && jsStr != null) {
            if (jsStr.containsKey("orderNo")) {
                boolean flag = saveMqOrder(jsStr.get("orderNo").toString());
                if (flag) {
                    mqRecord.setPersist((byte) 1);
                    mqRecordService.edit(mqRecord);
                }
            }

        }
    }

    //查出订单和产品详情，并入库
    private boolean saveMqOrder(String orderNo) {
        OrderBasicInfo orderBasicInfo = iOrderBasicInfoService.findOrderBasicInfoByOrderNo(orderNo, true);
        if (null != orderBasicInfo) {
            MqOrderInfo mqOrderInfo = new MqOrderInfo();
            BeanUtils.copyProperties(mqOrderInfo, orderBasicInfo);
            mqOrderInfo = iOrderService.insertOrder(mqOrderInfo);
            if (null != mqOrderInfo) {
                List<OrderProductBasicInfo> infos = orderBasicInfo.getOrderProductBasicInfos();
                for (OrderProductBasicInfo temp : infos) {
                    MqOrderProduct mqOrderProduct = new MqOrderProduct();
                    BeanUtils.copyProperties(mqOrderProduct, temp);
                    iOrderProductService.insertOrderProduct(mqOrderProduct);
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
