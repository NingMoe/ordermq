package cn.donut.ordermq.worker.order;


import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderProductService;
import cn.donut.ordermq.service.order.IOrderService;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParseException;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.model.order.basic.OrderProductBasicInfo;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 创建订单监听
 *
 * @author wangjiahao
 * 〈一句话功能简述〉<br>
 * 〈订单创建-MQ消息接受处理〉
 * @author LiYuAn
 * @create 2018/6/28
 * @sice 1.0.0
 */
@Slf4j
public class OrderCreateReceiver implements MessageListener {

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
        log.info("收到消息：==>{}" + msg.toString());
        MqRecord mqRecord = saveMsg(msg);
        if (mqRecord != null) {
            MqOrderInfo orderInfo = parse(mqRecord.getJsonContent());
            if (orderInfo != null) {
                try {
                    MqOrderInfo order = saveData(orderInfo);
                    if (order != null) {
                        //回写消息状态
                        mqRecord.setPersist((byte) 1);
                        mqRecordService.edit(mqRecord);
                        log.info("订单创建已完成！订单号：{}", order.getOrderNo());
                    }else {
                        log.info("订单已存在！");
                    }
                } catch (Exception e) {
                    log.error("插入订单和产品失败！",e);
                }
            }
        }
    }

    /**
     * 接收消息，将消息存入数据库，转换成订单对象并返回
     *
     * @param message
     * @return MqOrderInfo
     */
    private MqRecord saveMsg(Message message) {

        String json = new String(message.getBody(), Charset.defaultCharset());

        MqRecord record = new MqRecord();
        record.setJsonContent(json);
        record.setCreateTime(new Date());
        record.setPersist((byte) 0);
        record.setRoutingKey("order.create");
        return mqRecordService.insert(record);
    }

    private MqOrderInfo parse(String json) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            String orderNo = jsonObject.get("orderNo").toString();
            Integer userId = (Integer) jsonObject.get("userId");

            MqOrderInfo orderInfo = new MqOrderInfo();
            orderInfo.setOrderNo(orderNo);
            orderInfo.setUserId(userId);

            return orderInfo;
        } catch (JsonParseException e) {
            log.error("JSON格式有误！", e);
        } catch (NullPointerException e) {
            log.error("JSON缺少关键字！", e);
        }
        return null;
    }

    /**
     * 从订单中心拿到订单具体信息，插入我方数据库数据
     *
     * @param orderInfo
     * @return MqOrderInfo
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqOrderInfo saveData(MqOrderInfo orderInfo) throws Exception {
        OrderBasicInfo info = iOrderBasicInfoService.findOrderBasicInfoByOrderNo(orderInfo.getOrderNo(), true);
        BeanUtils.copyProperties(orderInfo,info);
        orderInfo.setId(null);
        MqOrderInfo mqOrderInfo = iOrderService.findOneByOrderNo(orderInfo.getOrderNo());
        if (mqOrderInfo == null) {
            MqOrderInfo order = iOrderService.insertOrder(orderInfo);
            for (OrderProductBasicInfo productBasicInfo : info.getOrderProductBasicInfos()) {
                MqOrderProduct product = new MqOrderProduct();
                BeanUtils.copyProperties(product, productBasicInfo);
                product.setId(null);
                MqOrderProduct orderProduct = iOrderProductService.insertOrderProduct(product);
                if (orderProduct == null) {
                    throw new Exception("插入产品失败！");
                }
                order.getMqOrderProducts().add(orderProduct);
            }
            return order;
        }
        return null;
    }

}
