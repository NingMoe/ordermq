package cn.donut.ordermq.mapper.order;

import cn.donut.ordermq.entity.order.MqOrderProduct;
import cn.donut.ordermq.entity.order.MqOrderProductExample;
import com.koolearn.framework.mybatis.annotation.DAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@DAO
public interface MqOrderProductMapper {
    int countByExample(MqOrderProductExample example);

    int deleteByExample(MqOrderProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MqOrderProduct record);

    int insertSelective(MqOrderProduct record);

    List<MqOrderProduct> selectByExample(MqOrderProductExample example);

    MqOrderProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MqOrderProduct record, @Param("example") MqOrderProductExample example);

    int updateByExample(@Param("record") MqOrderProduct record, @Param("example") MqOrderProductExample example);

    int updateByPrimaryKeySelective(MqOrderProduct record);

    int updateByPrimaryKey(MqOrderProduct record);
}