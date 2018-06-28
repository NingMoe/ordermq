package cn.donut.ordermq.impl.order;

import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderInfoExample;
import cn.donut.ordermq.mapper.order.MqOrderInfoMapper;
import cn.donut.ordermq.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangjiahao
 * @date 2018/6/28 18:46
 */
@Service
public class IOrderServiceImpl implements IOrderService {

    @Autowired
    private MqOrderInfoMapper orderInfoMapper;

    @Override
    public MqOrderInfo findOneByOrderNo(String orderNo) {
        MqOrderInfoExample example = new MqOrderInfoExample();
        example.createCriteria().andOrderNoEqualTo(orderNo);
        List<MqOrderInfo> infos = orderInfoMapper.selectByExample(example);
        if (infos.size()>0){
            return infos.get(0);
        }
        return null;
    }

    @Override
    public MqOrderInfo findOneByOrderId(Integer orderId) {
        MqOrderInfoExample example = new MqOrderInfoExample();
        example.createCriteria().andIdEqualTo(orderId);
        List<MqOrderInfo> infos = orderInfoMapper.selectByExample(example);
        if (infos.size()>0){
            return infos.get(0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public MqOrderInfo insertOrder(MqOrderInfo orderInfo) {
        int i = orderInfoMapper.insertSelective(orderInfo);
        return i>0?orderInfo:null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public MqOrderInfo editOrder(MqOrderInfo orderInfo) {
        int i = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        MqOrderInfo info = orderInfoMapper.selectByPrimaryKey(orderInfo.getId());
        return i>0?info:null;
    }
}
