package cn.donut.ordermq.service.order;

import cn.donut.ordermq.entity.order.MqOrderInfo;

import java.util.List;

/**
 * @author wangjiahao
 * @date 2018/6/28 18:35
 */
public interface IOrderService {

    /**
     * 按订单号查询订单
     * @param orderNo
     * @return
     */
    MqOrderInfo findOneByOrderNo(String orderNo);

    /**
     * 按订单ID查询订单
     * @param orderId
     * @return
     */
    MqOrderInfo findOneByOrderId(Integer orderId);

    /**
     * 插入订单
     * @param orderInfo
     * @return
     */
    MqOrderInfo insertOrder(MqOrderInfo orderInfo);

    /**
     * 修改订单
     * @param orderInfo
     * @return
     */
    MqOrderInfo editOrder(MqOrderInfo orderInfo);

    /**
     * 批量删除--假删除
     * @param orderIds
     * @return
     */
    Boolean deleteOrders(List<Integer> orderIds);

}
