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
package order.product;

import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.service.order.IOrderService;
import cn.donut.retailm.entity.domain.DrOrderInfo;
import cn.donut.retailm.entity.domain.DrOrderProduct;
import cn.donut.retailm.entity.model.OrderModel;
import com.google.common.collect.Maps;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfoWithPayway;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.sharks.model.Product;
import com.koolearn.sharks.service.IProductService;
import com.koolearn.sso.dto.UsersDTO;
import com.koolearn.sso.service.IOpenService;
import com.koolearn.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private IOrderService iOrderService;

    //鲨鱼产品查询接口
    @Autowired
    private IProductService productService;

    @Autowired
    private IOpenService iOpenService;

    @Autowired
    private MqUtil mqUtil;

    @Autowired
    private IOrderBasicInfoService iOrderBasicInfoService;


    @Autowired
    private cn.donut.retailm.service.order.IOrderService iRetailmOrderService;

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


    @RequestMapping(value = "/correctionOrder", method = RequestMethod.GET)
    @ResponseBody
    public int updateOrder(@RequestParam String OrderNo) throws Exception {
        MqOrderInfo mqOrderInfo = iOrderService.findOneByOrderNo(OrderNo);
        int a = 0;
        if (mqOrderInfo != null) {
            System.out.println("查询出订单:" + mqOrderInfo.toString());
            a = editRetailm(mqOrderInfo);
        }


        return a;
    }


    /**
     * 回写分销系统状态0:不需要回写，1true，2false
     */
    private int editRetailm(MqOrderInfo mqOrderInfo) throws Exception {
        System.out.println("回写,订单实体" + mqOrderInfo.toString());
        DrOrderInfo drOrderInfo = new DrOrderInfo();

        //从鲨鱼拿到订单并复制
        OrderBasicInfoWithPayway orderBasicInfoWithPayway = iOrderBasicInfoService.findOrderBasicInfoWithPaywayByOrderNo(mqOrderInfo.getOrderNo(), true);
        System.out.println("orderBasicInfoWithPayway=" + orderBasicInfoWithPayway.toString());
        Map<Integer, String> paywayMap = Maps.newHashMap();
        if(null != orderBasicInfoWithPayway){
            paywayMap = orderBasicInfoWithPayway.getPayWayMap();
            System.out.println("支付方式" + paywayMap != null);
        }

//        Map<Integer, String> paywayMap = mqOrderInfo.getPayWayMap();
        Map<String, Object> map = iRetailmOrderService.findOrderByTradeNo(mqOrderInfo.getOrderNo());
        System.out.println("订单号" + mqOrderInfo.getOrderNo());
        String payWay = mqUtil.getPayWay(paywayMap);
        System.out.println("分销订单：" + map.containsKey("orderInfo"));
        if (null != map && map.containsKey("orderInfo")) {
            System.out.println("分销系统有该订单，执行更新");
            drOrderInfo = (DrOrderInfo) map.get("orderInfo");
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //已支付
            drOrderInfo.setStatus((byte) 1);
            //分销员id
            Integer retailmId = mqUtil.getRetailMemberId(mqOrderInfo);
            if (null == retailmId) {
                return 0;
            }
            System.out.println("分销员不为空" + retailmId);
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
            //没订单数据，就要新增了
            System.out.println("分销系统没有该订单，执行新增");
            drOrderInfo.setTradeNumber(mqOrderInfo.getOrderNo());
            //分销员id

            Integer retailmId = mqUtil.getRetailMemberId(mqOrderInfo);

            if (null == retailmId) {
                return 0;
            }
            System.out.println("分销员不为空" + retailmId);
            drOrderInfo.setRetailMemberId(retailmId);
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
