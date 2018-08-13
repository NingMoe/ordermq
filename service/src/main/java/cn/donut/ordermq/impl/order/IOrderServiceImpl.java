package cn.donut.ordermq.impl.order;

import cn.donut.ordermq.dto.OrderBasicInfoDto;
import cn.donut.ordermq.entity.MqOrderPush;
import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderInfoExample;
import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.mapper.MqOrderPushMapper;
import cn.donut.ordermq.mapper.order.MqOrderInfoMapper;
import cn.donut.ordermq.service.order.IOrderProductService;
import cn.donut.ordermq.service.order.IOrderService;
import com.google.common.collect.Maps;
import com.koolearn.ordercenter.model.order.basic.OrderBasicInfo;
import com.koolearn.ordercenter.model.order.basic.OrderProductBasicInfo;
import com.koolearn.ordercenter.service.IOrderBasicInfoService;
import com.koolearn.util.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangjiahao
 * @date 2018/6/28 18:46
 */
@Service
public class IOrderServiceImpl implements IOrderService {

    private static final Logger log = LoggerFactory.getLogger(IOrderServiceImpl.class);

    @Autowired
    private MqOrderInfoMapper orderInfoMapper;

    @Autowired
    private IOrderBasicInfoService iOrderBasicInfoService;

    @Autowired
    private IOrderProductService iOrderProductService;

    @Autowired
    private MqOrderPushMapper mqOrderPushMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqOrderInfo editOrderAndProduct(MqOrderInfo orderInfo) throws Exception {
        OrderBasicInfo info = iOrderBasicInfoService.findOrderBasicInfoByOrderNo(orderInfo.getOrderNo(), true);

        MqOrderInfo one = findOneByOrderNo(info.getOrderNo());

        BeanUtils.copyProperties(info, orderInfo);
        info.setId(one.getId());

        MqOrderInfo order = editOrder(orderInfo);
        try {
            List<MqOrderProduct> products = updateProducts(info);
            if (products != null && products.size() > 0) {
                order.setMqOrderProducts(products);
            }
            return order;
        } catch (Exception e) {
            log.error("修改产品信息失败！", e);
            return null;
        }
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

    @Override
    public MqOrderInfo findOneByOrderNo(String orderNo) {
        MqOrderInfoExample example = new MqOrderInfoExample();
        example.createCriteria().andOrderNoEqualTo(orderNo);
        List<MqOrderInfo> infos = orderInfoMapper.selectByExample(example);
        if (infos.size() > 0) {
            return infos.get(0);
        }
        return null;
    }

    @Override
    public MqOrderInfo findOneByOrderId(Integer orderId) {
        MqOrderInfoExample example = new MqOrderInfoExample();
        example.createCriteria().andIdEqualTo(orderId);
        List<MqOrderInfo> infos = orderInfoMapper.selectByExample(example);
        if (infos.size() > 0) {
            return infos.get(0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqOrderInfo insertOrder(MqOrderInfo orderInfo) {
        int i = orderInfoMapper.insertSelective(orderInfo);
        return i > 0 ? orderInfo : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqOrderInfo editOrder(MqOrderInfo orderInfo) {
        int i = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        MqOrderInfo info = orderInfoMapper.selectByPrimaryKey(orderInfo.getId());
        return i > 0 ? info : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqOrderInfo saveOrder(MqOrderInfo orderInfo) throws Exception {
        OrderBasicInfo info = iOrderBasicInfoService.findOrderBasicInfoByOrderNo(orderInfo.getOrderNo(), true);
        BeanUtils.copyProperties(orderInfo, info);
        orderInfo.setId(null);
        orderInfo.setSharkOrderInfoId(info.getId());
        MqOrderInfo mqOrderInfo = findOneByOrderNo(orderInfo.getOrderNo());
        if (mqOrderInfo == null) {
            MqOrderInfo order = insertOrder(orderInfo);
            List<MqOrderProduct> list = new ArrayList<>();
            for (OrderProductBasicInfo productBasicInfo : info.getOrderProductBasicInfos()) {
                MqOrderProduct product = new MqOrderProduct();

                product = copyProperties(product, productBasicInfo);
                MqOrderProduct orderProduct = iOrderProductService.insertOrderProduct(product);
                if (orderProduct == null) {
                    throw new Exception("插入产品失败！");
                }
                list.add(orderProduct);

            }
            order.setMqOrderProducts(list);
            return order;
        }
        return null;
    }

    @Override
    public MqOrderProduct copyProperties(MqOrderProduct product, OrderProductBasicInfo productBasicInfo) {

        product.setProductstatus(productBasicInfo.getProductStatus());
        product.setExamseasonid(productBasicInfo.getExamSeasonId());
        product.setIsgiveproduct(productBasicInfo.getIsGiveProduct());
        product.setOrderno(productBasicInfo.getOrderNo());
        product.setOriginalprice(productBasicInfo.getOriginalPrice());
        product.setOriginalpricenetvalue(productBasicInfo.getOriginalPriceNetValue());
        product.setProductid(productBasicInfo.getProductId());
        product.setProductline(productBasicInfo.getProductLine());
        product.setProductname(productBasicInfo.getProductName());
        product.setProducttype(productBasicInfo.getProductType());
        product.setStrikeprice(productBasicInfo.getStrikePrice());
        return product;
    }


    @Override
    public Map<String,Object> checkProLine(MqOrderInfo orderInfo) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("flag",false);
        if (orderInfo == null) {
            return map;
        }
        MqOrderInfo mqOrderInfo = new MqOrderInfo();
        //去鲨鱼查询订单
        OrderBasicInfo info = iOrderBasicInfoService.findOrderBasicInfoByOrderNo(orderInfo.getOrderNo(), true);
        if (null == info) {
            return map;
        }
        String originalOrderNo=null;
        if(info.getBasicOrderId()!=null){
            originalOrderNo=orderInfoMapper.selectBySharkOrderInfoId(info.getBasicOrderId());
        }
        List<OrderProductBasicInfo> list = info.getOrderProductBasicInfos();
        if (null != list && list.size() > 0) {
            //判断产品线：订单包含产品要么全是多纳的，要么都不是多纳的
            if (list.get(0).getProductLine() == 49 || list.get(0).getProductLine() == 58) {
               
                map.put("flag",true);
                map.put("productLine",list.get(0).getProductLine());
                List<OrderBasicInfoDto> infoList=new ArrayList<>();
                for(int i=0;i<list.size();i++){
                    OrderBasicInfoDto orderBasicInfoDto=new OrderBasicInfoDto(info);
                    Integer productId = list.get(i).getProductId();
                    try {
                        MqOrderPush push = mqOrderPushMapper.selectByProductId(productId);
                        log.error("productId=="+productId);
                        log.error("push=="+push.toString());
                        if(push!=null){
                            String url = push.getUrl();
                            List<OrderProductBasicInfo> listN=new ArrayList<>();
                            listN.add(list.get(i));
                            orderBasicInfoDto.setOrderProductBasicInfos(listN);
                            orderBasicInfoDto.setUrl(url);
                            if(StringUtils.isNotEmpty(originalOrderNo)){
                                orderBasicInfoDto.setOriginalOrderNo(originalOrderNo);
                            }
                            infoList.add(orderBasicInfoDto);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        log.error("位置:checkProLine方法");
                        log.error("orderBasicInfoDto:"+orderBasicInfoDto.toString());
                        log.error("orderInfo:"+orderInfo.toString());
                    }
                }

                if (infoList.size()>0){
                    map.put("infoList",infoList);
                }
                return map;
            } else {
                return map;
            }
        }

        return map;
    }

    @Override
    public void a(MqOrderPush push) {
        int insert = mqOrderPushMapper.insert(push);
        System.out.println(insert);
    }


}
