package cn.donut.ordermq.mapper.order;

import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.entity.order.MqOrderInfoExample;
import com.koolearn.framework.mybatis.annotation.DAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@DAO
public interface MqOrderInfoMapper {
    int countByExample(MqOrderInfoExample example);

    int deleteByExample(MqOrderInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MqOrderInfo record);

    int insertSelective(MqOrderInfo record);

    List<MqOrderInfo> selectByExample(MqOrderInfoExample example);

    MqOrderInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MqOrderInfo record, @Param("example") MqOrderInfoExample example);

    int updateByExample(@Param("record") MqOrderInfo record, @Param("example") MqOrderInfoExample example);

    int updateByPrimaryKeySelective(MqOrderInfo record);

    int updateByPrimaryKey(MqOrderInfo record);
}