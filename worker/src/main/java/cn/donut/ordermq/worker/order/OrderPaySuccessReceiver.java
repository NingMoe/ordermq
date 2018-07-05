package cn.donut.ordermq.worker.order;

import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderProductService;
import cn.donut.ordermq.service.order.IOrderService;
import cn.donut.ordermq.worker.MqUtil;
import cn.donut.retailm.entity.domain.DrOrderInfo;
import cn.donut.retailm.entity.model.OrderModel;
import cn.donut.retailm.service.common.MsgEncryptionService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.koolearn.ordercenter.model.MQMessage;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.model.order.basic.OrderProductBasicInfo;
import com.koolearn.ordercenter.queue.OrderPaySuccessQueue;
import com.koolearn.ordercenter.queue.Queue;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.ordercenter.service.IOrderDistributionInfoService;
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

    @Autowired
    private MqUtil mqUtil;

    public void executor(final OrderPaySuccessQueue queue) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MQMessage message = buildMQMessage(queue);
                System.out.println("queue = " + queue.toString());
                String json = JSON.toJSONString(queue);
                log.info("收到消息：==>{}" + json);
                //转换
                MqOrderInfo orderInfo = mqUtil.Json2Order(json);
                //是否多纳订单
                boolean flag = iOrderService.checkProLine(orderInfo);
                if (orderInfo != null && flag) {
                    //保存
                    MqRecord mqRecord = mqUtil.saveMsg(json, "order.pay.success");
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
     * 业务处理:根据订单号查询最新实体，并更新本地数据
     *
     * @param orderInfo
     * @return MqOrderInfo
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqOrderInfo updateData(MqOrderInfo orderInfo) throws Exception {

        //从鲨鱼拿到订单并复制
        OrderBasicInfo info = iOrderBasicInfoService.findOrderBasicInfoByOrderNo(orderInfo.getOrderNo(), true);
        //查询本地数据
        MqOrderInfo one = iOrderService.findOneByOrderNo(orderInfo.getOrderNo());

        BeanUtils.copyProperties(orderInfo, info);
        MqOrderInfo order;
        //如果本地没有数据，执行新增，外层已执行产品线校验
        if (one != null) {
            orderInfo.setId(one.getId());
            order = iOrderService.editOrder(orderInfo);
            if (null != order) {
                try {
                    List<MqOrderProduct> products = updateProducts(info);
                    if (products != null && products.size() > 0) {
                        order.setMqOrderProducts(products);
                    }
                    return order;
                } catch (Exception e) {
                    log.error("修改产品状态失败！", e);
                    return null;
                }
            } else {
                log.error("修改产品信息失败！");
                return null;
            }

        } else {//新增
            orderInfo.setId(null);
            order = iOrderService.saveOrder(orderInfo);
        }
        return order;
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


    //回写分销系统状态
    private Boolean editRetailm(MqOrderInfo mqOrderInfo) {

        DrOrderInfo drOrderInfo = new DrOrderInfo();
        Map<String, Object> map = iRetailmOrderService.findOrderByTradeNo(mqOrderInfo.getOrderNo());

        if (null != map && map.containsKey("orderInfo")) {
            drOrderInfo = (DrOrderInfo) map.get("orderInfo");
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //已支付
            drOrderInfo.setStatus((byte) 1);
            //分销员id
            drOrderInfo.setRetailMemberId(mqUtil.getRetailMemberId(mqOrderInfo));
            drOrderInfo.setUpdateTime(new Date());
            return iRetailmOrderService.editOrder(drOrderInfo);
        } else {
            //没订单数据，就要新增了
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //分销员id
            drOrderInfo.setRetailMemberId(mqUtil.getRetailMemberId(mqOrderInfo));
            drOrderInfo.setUpdateTime(new Date());
            drOrderInfo.setStatus((byte) 1);
            drOrderInfo.setNetWorth(mqOrderInfo.getNetValue());
            drOrderInfo.setRealPrice(mqOrderInfo.getStrikePrice());
            drOrderInfo.setPayTime(mqOrderInfo.getPayTime());
            drOrderInfo.setOrderTime(mqOrderInfo.getOrderTime());
            drOrderInfo.setPrice(mqOrderInfo.getOriginalPrice());
            OrderModel orderModel = iRetailmOrderService.insertOrder(drOrderInfo);
            if (null != orderModel && null != orderModel.getId()) {
                return true;
            }
            return false;
        }

    }

}
