package cn.donut.ordermq.impl.order;

import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.entity.order.MqOrderProductExample;
import cn.donut.ordermq.mapper.order.MqOrderProductMapper;
import cn.donut.ordermq.service.order.IOrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjiahao
 * @date 2018/6/28 19:24
 */
@Service
public class IOrderProductServiceImpl implements IOrderProductService {

    @Autowired
    private MqOrderProductMapper productMapper;

    @Override
    public MqOrderProduct findOneById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MqOrderProduct> findProductsByOrderNo(String orderNo) {
        MqOrderProductExample example = new MqOrderProductExample();
        example.createCriteria().andOrdernoEqualTo(orderNo);
        return productMapper.selectByExample(example);
    }

    @Override
    public MqOrderProduct insertOrderProduct(MqOrderProduct product) {
        int i = productMapper.insertSelective(product);
        return i>0?product:null;
    }

    @Override
    public MqOrderProduct editOrderProduct(MqOrderProduct product) {
        int i = productMapper.updateByPrimaryKeySelective(product);
        MqOrderProduct orderProduct = productMapper.selectByPrimaryKey(product.getId());
        return i>0?orderProduct:null;
    }
}
