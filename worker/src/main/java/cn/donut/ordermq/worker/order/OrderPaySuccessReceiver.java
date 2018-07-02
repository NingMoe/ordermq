package cn.donut.ordermq.worker.order;

import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderProductService;
import cn.donut.ordermq.service.order.IOrderService;
import cn.donut.retailm.entity.domain.DrOrderInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParseException;
import com.koolearn.ordercenter.model.MQMessage;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.model.order.basic.OrderProductBasicInfo;
import com.koolearn.ordercenter.queue.OrderPaySuccessQueue;
import com.koolearn.ordercenter.queue.Queue;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 支付成功监听
 *
 * @author wangjiahao
 * @date 2018/6/29 9:31
 */
@Slf4j
public class OrderPaySuccessReceiver {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private MqRecordService mqRecordService;
    @Autowired
    private IOrderBasicInfoService iOrderBasicInfoService;
    @Autowired
    private IOrderProductService iOrderProductService;

    @Autowired
    private cn.donut.retailm.service.order.IOrderService iRetailmOrderService;

    public void executor(final OrderPaySuccessQueue queue) {
        taskExecutor.execute(new Runnable() {

            @Override
            public void run() {
                MQMessage message = buildMQMessage(queue);
                System.out.println("queue = " + queue.toString());
                String json = JSON.toJSONString(queue);
                log.info("收到消息：==>{}" + json);
//                转换
                MqOrderInfo orderInfo = parse(json);
                if (orderInfo != null) {
                    //保存
                    MqRecord mqRecord = saveMsg(json);
                    if (mqRecord != null) {
                        try {
                            MqOrderInfo order = updateData(orderInfo);
                            if (order != null) {
                                //回写消息状态
                                mqRecord.setPersist((byte) 1);
                                mqRecordService.edit(mqRecord);
                                if (editRetailm(order)) {
                                    log.info("分销系统订单回写成功！订单号：{}", order.getOrderNo());
                                } else {
                                    log.info("分销系统订单回写失败！订单号：{}", order.getOrderNo());
                                }
                                log.info("订单支付已完成！订单号：{}", order.getOrderNo());
                            } else {
                                log.info("订单支付记录插入或修改失败！");
                            }
                        } catch (Exception e) {
                            log.error("插入订单支付记录失败！", e);
                        }
                    }
                }
//         TODO: 2018/6/29 做出分发
//         TODO: 2018/6/29 分发记录
            }
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
        record.setRoutingKey("order.pay.success");
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
     * 从订单中心拿到订单具体信息，插入我方数据库数据
     *
     * @param orderInfo
     * @return MqOrderInfo
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqOrderInfo updateData(MqOrderInfo orderInfo) {
        OrderBasicInfo info = iOrderBasicInfoService.findOrderBasicInfoByOrderNo(orderInfo.getOrderNo(), true);
        BeanUtils.copyProperties(orderInfo, info);
        MqOrderInfo order = iOrderService.findOneByOrderNo(orderInfo.getOrderNo());
        MqOrderInfo result;
        if (order != null) {
            orderInfo.setId(order.getId());
            result = iOrderService.editOrder(orderInfo);
            try {
                List<MqOrderProduct> products = updateProducts(info);
                if (products != null && products.size() > 0) {
                    result.setMqOrderProducts(products);
                }
            } catch (Exception e) {
                log.error("修改产品状态失败！", e);
                return null;
            }
        } else {
            orderInfo.setId(null);
            result = iOrderService.insertOrder(orderInfo);
            try {
                List<MqOrderProduct> products = saveProducts(info);
                if (products != null && products.size() > 0) {
                    result.setMqOrderProducts(products);
                }
            } catch (Exception e) {
                log.error("插入产品信息失败！", e);
                return null;
            }
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<MqOrderProduct> updateProducts(OrderBasicInfo orderBasicInfo) throws Exception {
        if (orderBasicInfo.getOrderProductBasicInfos() != null && orderBasicInfo.getOrderProductBasicInfos().size() > 0) {
            List<OrderProductBasicInfo> basicInfos = orderBasicInfo.getOrderProductBasicInfos();
            for (OrderProductBasicInfo i : basicInfos) {
                MqOrderProduct product = new MqOrderProduct();
                product.setProductstatus(i.getProductStatus());
                Boolean flag = iOrderProductService.editProductsByOrderNo(orderBasicInfo.getOrderNo(), product);
                if (!flag) {
                    throw new Exception("修改产品状态失败！");
                }
            }
            return iOrderProductService.findProductsByOrderNo(orderBasicInfo.getOrderNo());
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<MqOrderProduct> saveProducts(OrderBasicInfo info) throws Exception {
        if (info.getOrderProductBasicInfos() != null && info.getOrderProductBasicInfos().size() > 0) {
            List<MqOrderProduct> list = new ArrayList<MqOrderProduct>();
            for (OrderProductBasicInfo productBasicInfo : info.getOrderProductBasicInfos()) {
                MqOrderProduct product = new MqOrderProduct();
                BeanUtils.copyProperties(product, productBasicInfo);
                product.setId(null);
                MqOrderProduct orderProduct = iOrderProductService.insertOrderProduct(product);
                if (orderProduct == null) {
                    throw new Exception("插入产品失败！");
                }
                list.add(orderProduct);
            }
            return list;
        }
        return null;
    }

    public static String ip() {
        String ip = "";
        try {
            //获得本机IP　　
            ip = InetAddress.getLocalHost().getHostAddress().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }


    private MQMessage buildMQMessage(Queue queue) {
        return new MQMessage()
                .setExchange(queue.exchange())
                .setRoutingKey(queue.routingKey())
                .setQueueName("donut.order.pay.success")
                .setMessageBody(JSONObject.toJSONString(queue))
                .setConsumerIp(ip());
    }


    //回写状态
    private Boolean editRetailm(MqOrderInfo mqOrderInfo) {
        DrOrderInfo drOrderInfo = new DrOrderInfo();
        Map<String, Object> map = iRetailmOrderService.findOrderByTradeNo(mqOrderInfo.getOrderNo());
        if (map.size() > 0 && map.containsKey("orderInfo")) {
            drOrderInfo = (DrOrderInfo) map.get("orderInfo");
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //已支付
            drOrderInfo.setStatus((byte) 1);
            drOrderInfo.setUpdateTime(new Date());
            return iRetailmOrderService.editOrder(drOrderInfo);
        } else {
            return false;
        }

    }

}
