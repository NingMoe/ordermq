package cn.donut.ordermq.worker.order;

import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderProductService;
import cn.donut.ordermq.service.order.IOrderService;
import cn.donut.ordermq.worker.MqUtil;
import cn.donut.retailm.entity.domain.DrOrderInfo;
import cn.donut.retailm.entity.domain.DrOrderProduct;
import cn.donut.retailm.entity.model.OrderModel;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfoWithPayway;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.sso.dto.UsersDTO;
import com.koolearn.sso.service.IOpenService;
import com.koolearn.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.*;

/**
 * 订单退课监听
 *
 * @author wangjiahao
 * @date 2018/6/29 9:31
 */
@Slf4j
public class OrderRefundReceiver implements MessageListener {

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
    private IOpenService iOpenService;

    @Autowired
    private MqUtil mqUtil;

    @Override
    public void onMessage(final Message msg) {

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String json = new String(msg.getBody(), Charset.defaultCharset());
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
                    MqRecord mqRecord = mqUtil.saveMsg(json, "order.refund");
                    if (mqRecord != null) {
                        try {
                            //根据订单号查询最新实体，并更新本地数据
                            MqOrderInfo order = updateData(orderInfo);
                            if (order != null) {
                                //回写消息状态
                                mqRecord.setPersist((byte) 1);
                                mqRecordService.edit(mqRecord);
                                if (map.containsKey("productLine")) {
                                    Integer lineCode = (Integer) map.get("productLine");
//                                    if (lineCode == 49) {
//                                        //推送直播
//                                        try {
//                                            Boolean live = mqUtil.pushLive(order);
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                            editRetailm(order);
//                                        }
//
//                                    }
                                }
                                Boolean retailm = editRetailm(order);
                                System.out.println("执行完成回写分销系统" + retailm);
                                if (retailm) {
                                    log.info("分销系统订单回写成功！订单号：{}", order.getOrderNo());
                                } else if (null == retailm) {
                                    log.info("不需要回写！订单号：{}");
                                } else {
                                    log.info("分销系统订单回写失败！订单号：{}", order.getOrderNo());
                                }
                                log.info("订单更新已完成！订单号：{}", order.getOrderNo());
                            } else {
                                log.info("订单更新失败！");
                            }
                        } catch (Exception e) {
                            log.error("更新订单和产品失败！", e);
                        }
                    }
                }
                // TODO: 2018/6/29 做出分发
                // TODO: 2018/6/29 分发记录
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
        //查询本地数据
        MqOrderInfo one = iOrderService.findOneByOrderNo(orderInfo.getOrderNo());
        Map<Integer, String> payWayMap = orderBasicInfoWithPayway.getPayWayMap();

        BeanUtils.copyProperties(orderInfo, info);
        MqOrderInfo order;
        //如果本地没有数据，执行新增，外层已执行产品线校验
        if (one != null) {
            orderInfo.setId(one.getId());
            orderInfo.setNetValue(info.getOriginalPriceNetValue());
            order = iOrderService.editOrder(orderInfo);
            if (null != order) {
                try {
                    List<MqOrderProduct> list = mqUtil.updateProducts(info);
                    //TODO 是否要判空
                    if (list != null && list.size() > 0) {
                        order.setMqOrderProducts(list);
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

    //回写状态
    private Boolean editRetailm(MqOrderInfo mqOrderInfo) throws Exception {
        System.out.println("回写,订单实体" + mqOrderInfo.toString());
        DrOrderInfo drOrderInfo = new DrOrderInfo();
        Map<String, Object> map = iRetailmOrderService.findOrderByTradeNo(mqOrderInfo.getOrderNo());
        Map<Integer, String> paywayMap = mqOrderInfo.getPayWayMap();
        String payWay = mqUtil.getPayWay(paywayMap);
        System.out.println("订单号" + mqOrderInfo.getOrderNo());
        if (map != null && map.containsKey("orderInfo")) {
            System.out.println("分销系统有该订单，执行更新");
            drOrderInfo = (DrOrderInfo) map.get("orderInfo");
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //已退款
            drOrderInfo.setStatus((byte) 2);
            //分销员id
            Integer retailmId = mqUtil.getRetailMemberId(mqOrderInfo);
            if (null == retailmId) {
                return null;
            }
            drOrderInfo.setUpdateTime(new Date());
            drOrderInfo.setRetailMemberId(retailmId);
            drOrderInfo.setPayWay(payWay);
            //查询客户信息
            UsersDTO userInfo = iOpenService.getUserById(mqOrderInfo.getUserId());
            if (userInfo != null) {
                drOrderInfo.setConsumerName(userInfo.getUserName());
                drOrderInfo.setConsumerPhone(userInfo.getMobile());
            }
            log.warn("回写分销系统drOrderInfo:-->{}", drOrderInfo.toString());
            return iRetailmOrderService.editOrder(drOrderInfo);
        } else {
            //没订单数据，就要新增了
            System.out.println("分销系统有该订单，执行新增");
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //分销员id

            Integer retailmId = mqUtil.getRetailMemberId(mqOrderInfo);

            if (null == retailmId) {
                return null;
            }
            System.out.println("分销员不为空" + retailmId);
            drOrderInfo.setRetailMemberId(retailmId);
            drOrderInfo.setUpdateTime(new Date());
            drOrderInfo.setStatus((byte) 2);
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
            log.warn("回写分销系统，订单信息drOrderInfo:-->{}", drOrderInfo.toString());
            List<MqOrderProduct> mqOrderProducts = mqOrderInfo.getMqOrderProducts();
            List<DrOrderProduct> orderProducts = new ArrayList<DrOrderProduct>();
            if (null != mqOrderProducts) {
                for (MqOrderProduct info : mqOrderProducts) {
                    log.warn("产品信息：-->{}", info.toString());
                    DrOrderProduct drOrderProduct = new DrOrderProduct();
                    BeanUtils.copyProperties(drOrderProduct, info);
                    orderProducts.add(drOrderProduct);
                }
            }
//            BeanUtils.copyProperties(orderProducts, mqOrderProducts);
            OrderModel orderModel = iRetailmOrderService.insertOrder(drOrderInfo, orderProducts);
            if (null != orderModel && null != orderModel.getId()) {
                System.out.println("分销系统订单新增成功");
                return true;
            }
            return false;
        }

    }

    /**
     * Aop的切点方法
     */
    public void pushAop() {
        System.out.println("执行业务处理--------------");
    }
}
