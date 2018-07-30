/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MqUtil
 * Author:   LiYuAn
 * Date:     2018/7/5 16:09
 * Description: 工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.worker;

import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.service.MqRecordService;
import cn.donut.ordermq.service.order.IOrderProductService;
import cn.donut.retailm.entity.domain.DrOrderInfo;
import cn.donut.retailm.entity.domain.DrOrderProduct;
import cn.donut.retailm.entity.model.OrderModel;
import cn.donut.retailm.service.common.MsgEncryptionService;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParseException;
import com.koolearn.ordercenter.model.OrderDistributionInfo;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.model.order.basic.OrderProductBasicInfo;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.ordercenter.service.IOrderDistributionInfoService;
import com.koolearn.sso.dto.UsersDTO;
import com.koolearn.sso.service.IOpenService;
import com.koolearn.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈工具类〉
 *
 * @author LiYuAn * @create 2018/7/5C
 * @since 1.0.0
 */
@Slf4j
@Component
public class MqUtil {

    @Autowired
    private IOrderDistributionInfoService iOrderDistributionInfoService;

    @Autowired
    private MsgEncryptionService msgEncryptionService;

    @Autowired
    private MqRecordService mqRecordService;

    @Autowired
    private IOrderProductService iOrderProductService;

//    @Autowired
//    private SystemAllocationService systemAllocationService;

    @Autowired
    private IOrderBasicInfoService iOrderBasicInfoService;

    @Autowired
    private cn.donut.retailm.service.order.IOrderService iRetailmOrderService;

    @Autowired
    private IOpenService iOpenService;

    /**
     * json转化实体
     *
     * @param json
     * @return
     */
    public MqOrderInfo Json2Order(String json) {
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
     * 获取解密后的分销员id
     *
     * @param mqOrderInfo
     * @return
     */
    public Integer getRetailMemberId(MqOrderInfo mqOrderInfo) {
        System.out.println("查询分销员");
        OrderDistributionInfo orderDistributionInfo = iOrderDistributionInfoService.findOrderDistributionInfoByOrderNo(mqOrderInfo.getOrderNo());
        if (null != orderDistributionInfo) {
            //解密分销员id
            System.out.println("分销员信息不为空" + orderDistributionInfo.toString());
            String id = null;
            if (orderDistributionInfo.getDistributionUser() == null) {
                return null;
            }
            try {
                System.out.println("分销员id" + orderDistributionInfo.getDistributionUser());
                id = msgEncryptionService.decryption(orderDistributionInfo.getDistributionUser());
                System.out.println("分销员id=" + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Integer.valueOf(id);
        }
        return null;
    }

    /**
     * 接收消息，将消息存入数据库，转换成订单对象并返回
     *
     * @param json
     * @return MqOrderInfo
     */
    public MqRecord saveMsg(String json, String routingKey) {
        MqRecord record = new MqRecord();
        record.setJsonContent(json);
        record.setCreateTime(new Date());
        record.setPersist((byte) 0);
        record.setRoutingKey(routingKey);
        return mqRecordService.insert(record);
    }


    /**
     * 更新订单时，要同时更新产品
     *
     * @param orderBasicInfo
     * @return
     * @throws Exception
     */
    public List<MqOrderProduct> updateProducts(OrderBasicInfo orderBasicInfo) throws Exception {
        //判空
        if (orderBasicInfo.getOrderProductBasicInfos() != null && orderBasicInfo.getOrderProductBasicInfos().size() > 0) {
            List<OrderProductBasicInfo> basicInfos = orderBasicInfo.getOrderProductBasicInfos();
            for (OrderProductBasicInfo i : basicInfos) {
                //拿到产品，遍历
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

    public String getPayWay(Map<Integer, String> paywayMap) {
        String payWay = "";
        if (payWay == null) {
            return payWay;
        }

        Iterator iter = paywayMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            payWay += val + ",";
        }
        payWay = payWay.substring(0, payWay.length() - 1);
        return payWay;
    }

    //推送直播
//    public Boolean pushLive(MqOrderInfo order) {
//        System.out.println("本地订单实体:" + order.toString());
//        OrderBasicInfo orderBasicInfo = iOrderBasicInfoService.findOrderBasicInfoByOrderNo(order.getOrderNo(), true);
//        System.out.println("鲨鱼订单:" + orderBasicInfo.toString());
//        Integer i = systemAllocationService.addSystemAllocationTwoService(orderBasicInfo, convertDate(), 0);
//
//        return i > 0;
//    }

    public String convertDate() {
        Date date = new Date();
        SimpleDateFormat df = null;
        String returnValue = "";

        if (date == null) {
            // log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat("yyyyMMdd");
            returnValue = df.format(date);
        }

        return (returnValue);

    }

    //订单推送到分销系统 0:不需要回写，1true，2false
    public int pushOrderToRetailm(MqOrderInfo mqOrderInfo) throws Exception {
        System.out.println("推送分销系统,订单实体" + mqOrderInfo.toString());
        DrOrderInfo drOrderInfo = new DrOrderInfo();
        //1.先取到通用数据:支付方式，分销员id,如果没有分销员id，直接退出方法
        Map<Integer, String> paywayMap = mqOrderInfo.getPayWayMap();
        String payWay = getPayWay(paywayMap);
        Integer retailmId = getRetailMemberId(mqOrderInfo);
        if (null == retailmId) {
            return 0;
        }
        System.out.println("分销员不为空" + retailmId);
        //2.先根据订单号查询分销系统是否有订单
        Map<String, Object> map = iRetailmOrderService.findOrderByTradeNo(mqOrderInfo.getOrderNo());
        if (null != map && map.containsKey("orderInfo")) {
            System.out.println("分销系统有该订单，执行更新");
            drOrderInfo = (DrOrderInfo) map.get("orderInfo");
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //支付状态
            drOrderInfo.setStatus(mqOrderInfo.getStatus());
            //分销员id
            drOrderInfo.setRetailMemberId(retailmId);
            drOrderInfo.setPayWay(payWay);
            //查询客户信息
            UsersDTO userInfo = iOpenService.getUserById(mqOrderInfo.getUserId());
            if (userInfo != null) {
                drOrderInfo.setConsumerName(userInfo.getUserName());
                drOrderInfo.setConsumerPhone(userInfo.getMobile());
            }
            drOrderInfo.setUpdateTime(new Date());
            log.warn("回写分销系统drOrderInfo:-->{}", drOrderInfo.toString());
            return iRetailmOrderService.editOrder(drOrderInfo) ? 1 : 2;
        } else {
            System.out.println("分销系统没有该订单，执行新增");
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //分销员id
            drOrderInfo.setRetailMemberId(retailmId);
            drOrderInfo.setUpdateTime(new Date());
            drOrderInfo.setStatus(mqOrderInfo.getStatus());
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

            OrderModel orderModel = iRetailmOrderService.insertOrder(drOrderInfo, orderProducts);

            if (null != orderModel && null != orderModel.getId()) {
                System.out.println("分销系统订单新增成功");
                return 1;
            }
            return 2;
        }
    }


}