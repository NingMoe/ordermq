package cn.donut.ordermq.worker.order;

import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderService;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParseException;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 取消订单监听
 *
 * @author wangjiahao
 * @date 2018/6/29 9:31
 */
@Slf4j
public class OrderCancelReceiver implements MessageListener {


    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private MqRecordService mqRecordService;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IOrderBasicInfoService iOrderBasicInfoService;
//    {
//        "orderNo": "订单号",
//        "productIdList": [产品id1],
//        "userId": 下单用户id
//    }


    @Override
    public void onMessage(final Message message) {

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String json = new String(message.getBody(), Charset.defaultCharset());
                log.info("收到消息：==>{}" + json);
                //转化
                MqOrderInfo orderInfo = parse(json);
                if (orderInfo != null) {
                    //保存
                    MqRecord mqRecord = saveMsg(json);
                    if (orderInfo != null) {
                        //更新订单数据库
                        MqOrderInfo order = updateData(orderInfo);
                        if (order != null) {
                            //回写消息状态
                            mqRecord.setPersist((byte) 1);
                            mqRecordService.edit(mqRecord);
                            log.info("订单取消已更新！订单号：{}", order.getOrderNo());
                        }
                    }
                }
            }
            // TODO: 2018/6/29 做出分发
            // TODO: 2018/6/29 分发记录
        });
    }

    /**
     * 接收消息，将消息存入数据库，转换成订单对象并返回
     *
     * @param json
     * @return MqOrderInfo
     */
    private MqRecord saveMsg(String json) {
        MqRecord record = new MqRecord();
        record.setJsonContent(json);
        record.setCreateTime(new Date());
        record.setPersist((byte) 0);
        record.setRoutingKey("order.cancel");
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
        } catch (Exception e) {
            log.error("其他异常！", e);
        }
        return null;
    }

    /**
     * 从订单中心拿到订单具体信息，更新我方数据库数据
     *
     * @param orderInfo
     * @return MqOrderInfo
     */
    private MqOrderInfo updateData(MqOrderInfo orderInfo) {
        OrderBasicInfo info = iOrderBasicInfoService.findOrderBasicInfoByOrderNo(orderInfo.getOrderNo(), false);

        MqOrderInfo one = iOrderService.findOneByOrderNo(info.getOrderNo());

        BeanUtils.copyProperties(info, orderInfo);
        info.setId(one.getId());
        return iOrderService.editOrder(orderInfo);
    }
}
