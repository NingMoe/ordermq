package cn.donut.ordermq.mapper;

import cn.donut.ordermq.entity.MqOrderPush;
import cn.donut.ordermq.entity.MqOrderPushExample;
import com.koolearn.framework.mybatis.annotation.DAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * dao层接口
 *
 * @author luoxuzheng
 * @create 2018-06-05 15:40
 **/
@DAO
public interface MqOrderPushMapper {
    int countByExample(MqOrderPushExample example);

    int deleteByExample(MqOrderPushExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MqOrderPush record);

    int insertSelective(MqOrderPush record);

    List<MqOrderPush> selectByExample(MqOrderPushExample example);

    MqOrderPush selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MqOrderPush record, @Param("example") MqOrderPushExample example);

    int updateByExample(@Param("record") MqOrderPush record, @Param("example") MqOrderPushExample example);

    int updateByPrimaryKeySelective(MqOrderPush record);

    int updateByPrimaryKey(MqOrderPush record);

    MqOrderPush selectByProductId(Integer productId);

}
