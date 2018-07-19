package cn.donut.ordermq.worker.order;

import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderService;
import cn.donut.ordermq.worker.MqUtil;
import cn.donut.retailm.entity.domain.DrOrderInfo;
import cn.donut.retailm.entity.domain.DrOrderProduct;
import cn.donut.retailm.entity.model.OrderModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.koolearn.ordercenter.model.MQMessage;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfoWithPayway;
import com.koolearn.ordercenter.queue.OrderPaySuccessQueue;
import com.koolearn.ordercenter.queue.Queue;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.sso.dto.UsersDTO;
import com.koolearn.sso.service.IOpenService;
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
    private cn.donut.retailm.service.order.IOrderService iRetailmOrderService;

    @Autowired
    private IOpenService iOpenService;

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
                Map<String, Object> map = iOrderService.checkProLine(orderInfo);
                Boolean flag = false;
                if (null != map && map.containsKey("flag")) {
                    flag = (Boolean) map.get("flag");
                }
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
        OrderBasicInfoWithPayway orderBasicInfoWithPayway = iOrderBasicInfoService.findOrderBasicInfoWithPaywayByOrderNo(orderInfo.getOrderNo(), true);
        OrderBasicInfo info = orderBasicInfoWithPayway.getOrderBasicInfo();

        Map<Integer, String> payWayMap = orderBasicInfoWithPayway.getPayWayMap();
        //查询本地数据
        MqOrderInfo one = iOrderService.findOneByOrderNo(orderInfo.getOrderNo());

        BeanUtils.copyProperties(orderInfo, info);
        MqOrderInfo order;
        //如果本地没有数据，执行新增，外层已执行产品线校验
        if (one != null) {
            orderInfo.setId(one.getId());
            orderInfo.setNetValue(info.getOriginalPriceNetValue());
            order = iOrderService.editOrder(orderInfo);
            if (null != order) {
                try {
                    List<MqOrderProduct> products = mqUtil.updateProducts(info);
                    if (products != null && products.size() > 0) {
                        order.setMqOrderProducts(products);
                    }
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
            orderInfo.setNetValue(info.getOriginalPriceNetValue());
            order = iOrderService.saveOrder(orderInfo);
        }
        order.setPayWayMap(payWayMap);
        return order;
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
    private Boolean editRetailm(MqOrderInfo mqOrderInfo) throws Exception {
        System.out.println("回写分销系统");
        DrOrderInfo drOrderInfo = new DrOrderInfo();

        Map<Integer, String> paywayMap = mqOrderInfo.getPayWayMap();
        Map<String, Object> map = iRetailmOrderService.findOrderByTradeNo(mqOrderInfo.getOrderNo());
        String payWay = mqUtil.getPayWay(paywayMap);
        if (null != map && map.containsKey("orderInfo")) {
            System.out.println("分销系统有该订单，执行更新");
            drOrderInfo = (DrOrderInfo) map.get("orderInfo");
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //已支付
            drOrderInfo.setStatus((byte) 1);
            //分销员id
            drOrderInfo.setRetailMemberId(mqUtil.getRetailMemberId(mqOrderInfo));
            drOrderInfo.setPayWay(payWay);
            //查询客户信息
            UsersDTO userInfo = iOpenService.getUserById(mqOrderInfo.getUserId());
            if (userInfo != null) {
                drOrderInfo.setConsumerName(userInfo.getUserName());
                drOrderInfo.setConsumerPhone(userInfo.getMobile());
            }

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
            drOrderInfo.setPayWay(payWay);
            //查询客户信息
            UsersDTO userInfo = iOpenService.getUserById(mqOrderInfo.getUserId());
            if (userInfo != null) {
                drOrderInfo.setConsumerName(userInfo.getUserName());
                drOrderInfo.setConsumerPhone(userInfo.getMobile());
            }

            System.out.println("分销系统没有该订单，执行新增");
            System.out.println("订单信息:" + drOrderInfo.toString());
            List<MqOrderProduct> mqOrderProducts = mqOrderInfo.getMqOrderProducts();
            List<DrOrderProduct> orderProducts = new ArrayList<DrOrderProduct>();
            BeanUtils.copyProperties(orderProducts, mqOrderProducts);
            OrderModel orderModel = iRetailmOrderService.insertOrder(drOrderInfo, orderProducts);


            if (null != orderModel && null != orderModel.getId()) {
                System.out.println("分销系统订单新增成功");
                return true;
            }
            return false;
        }

    }

}
