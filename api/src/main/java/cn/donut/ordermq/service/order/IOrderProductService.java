package cn.donut.ordermq.service.order;

import cn.donut.ordermq.entity.order.MqOrderProduct;

import java.util.List;

/**
 * @author wangjiahao
 * @date 2018/6/28 19:21
 */
public interface IOrderProductService {

    /**
     * 按产品ID查询产品
     * @param id
     * @return
     */
    MqOrderProduct findOneById(Integer id);
    /**
     * 按订单号查询产品
     * @param orderNo
     * @return
     */
    List<MqOrderProduct> findProductsByOrderNo(String orderNo);

    /**
     * 插入产品
     * @param product
     * @return
     */
    MqOrderProduct insertOrderProduct(MqOrderProduct product);

    /**
     * 修改产品
     * @param product
     * @return
     */
    MqOrderProduct editOrderProduct(MqOrderProduct product);

}
