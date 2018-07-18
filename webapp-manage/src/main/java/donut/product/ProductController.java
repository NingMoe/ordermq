/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ProductController
 * Author:   LiYuAn
 * Date:     2018/7/6 14:31
 * Description: 产品服务接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package donut.product;

import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.service.order.IOrderService;
import cn.donut.retailm.entity.domain.DrOrderInfo;
import cn.donut.retailm.entity.model.OrderModel;
import com.google.common.collect.Maps;
import com.koolearn.sharks.model.Product;
import com.koolearn.sharks.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈产品服务接口〉
 *
 * @author LiYuAn
 * @create 2018/7/6
 * @since 1.0.0
 */
@Controller
@RequestMapping("transfer/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    //鲨鱼产品查询接口
    @Autowired
    private IProductService productService;


    @Autowired
    private IOrderService iOrderService;


    @Autowired
    private cn.donut.retailm.service.order.IOrderService iRetailmOrderService;

    @Autowired
    private MqUtil mqUtil;

    @RequestMapping(value = "/url", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getUrl(@RequestParam Integer productId) {
        Map<String, String> map = Maps.newHashMap();
        map.put("url", "");
        if (productId == null) {
            return map;
        }
        Product product = productService.findProductById(productId);
        if (null != product) {
            map.put("url", product.getProductInfoUrl());
            return map;
        }

        return map;
    }




    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Boolean test(@RequestParam Integer orderId) {
        System.out.println("回写分销系统");
        MqOrderInfo mqOrderInfo = iOrderService.findOneByOrderId(orderId);
        DrOrderInfo drOrderInfo = new DrOrderInfo();
        Map<String, Object> map = iRetailmOrderService.findOrderByTradeNo(mqOrderInfo.getOrderNo());

        if (null != map && map.containsKey("orderInfo")) {
            System.out.println("分销系统有该订单，执行更新");
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
            System.out.println("分销系统没有该订单，执行新增");
            OrderModel orderModel = iRetailmOrderService.insertOrder(drOrderInfo);

            if (null != orderModel && null != orderModel.getId()) {
                System.out.println("分销系统订单新增成功");
                return true;
            }
            return false;
        }

    }

}
