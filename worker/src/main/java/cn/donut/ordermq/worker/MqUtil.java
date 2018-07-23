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
import cn.donut.retailm.service.common.MsgEncryptionService;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParseException;
import com.koolearn.ordercenter.model.OrderDistributionInfo;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.model.order.basic.OrderProductBasicInfo;
import com.koolearn.ordercenter.service.IOrderDistributionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        OrderDistributionInfo orderDistributionInfo = iOrderDistributionInfoService.findOrderDistributionInfoByOrderNo(mqOrderInfo.getOrderNo());
        if (null != orderDistributionInfo) {
            //解密分销员id
            System.out.println("分销员信息不为空"+orderDistributionInfo.toString());
            String id = null;
            try {
                System.out.println("分销员id"+orderDistributionInfo.getDistributionUser());
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
//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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
}