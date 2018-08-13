package cn.donut.ordermq.service.order;

import cn.donut.ordermq.entity.MqOrderPush;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import com.koolearn.ordercenter.model.order.basic.OrderProductBasicInfo;

import java.util.Map;

/**
 * @author wangjiahao
 * @date 2018/6/28 18:35
 */
public interface IOrderService {

    /**
     * 按订单号查询订单
     *
     * @param orderNo
     * @return
     */
    MqOrderInfo findOneByOrderNo(String orderNo);

    /**
     * 按订单ID查询订单
     *
     * @param orderId
     * @return
     */
    MqOrderInfo findOneByOrderId(Integer orderId);

    /**
     * 插入订单
     *
     * @param orderInfo
     * @return
     */
    MqOrderInfo insertOrder(MqOrderInfo orderInfo);

    /**
     * 修改订单
     *
     * @param orderInfo
     * @return
     */
    MqOrderInfo editOrder(MqOrderInfo orderInfo);


    /**
     * 插入订单和产品
     *
     * @param orderInfo
     * @return
     */
    MqOrderInfo saveOrder(MqOrderInfo orderInfo) throws Exception;

    /**
     * 修改订单和产品
     *
     * @param orderInfo
     * @return
     * @throws Exception
     */
    MqOrderInfo editOrderAndProduct(MqOrderInfo orderInfo) throws Exception;

    /**
     * 复制实体
     *
     * @param product
     * @param productBasicInfo
     * @return
     */

    MqOrderProduct copyProperties(MqOrderProduct product, OrderProductBasicInfo productBasicInfo);

    /**
     * 判断是否为多纳订单
     *
     * @param orderInfo
     * @return
     */
    Map<String, Object> checkProLine(MqOrderInfo orderInfo);


    void test(MqOrderPush push);
}
