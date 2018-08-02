package cn.donut.ordermq.mapper;

import cn.donut.ordermq.entity.MqPushFailure;
import cn.donut.ordermq.entity.MqPushFailureExample;
import com.koolearn.framework.mybatis.annotation.DAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DAO
public interface MqPushFailureDao {
    int countByExample(MqPushFailureExample example);

    int deleteByExample(MqPushFailureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MqPushFailure record);

    int insertSelective(MqPushFailure record);

    List<MqPushFailure> selectByExample(MqPushFailureExample example);

    MqPushFailure selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MqPushFailure record, @Param("example") MqPushFailureExample example);

    int updateByExample(@Param("record") MqPushFailure record, @Param("example") MqPushFailureExample example);

    int updateByPrimaryKeySelective(MqPushFailure record);

    int updateByPrimaryKey(MqPushFailure record);
}