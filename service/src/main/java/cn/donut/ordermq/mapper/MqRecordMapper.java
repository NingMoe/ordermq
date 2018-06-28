package cn.donut.ordermq.mapper;

import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.entity.MqRecordExample;
import com.koolearn.framework.mybatis.annotation.DAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DAO
public interface MqRecordMapper {
    int countByExample(MqRecordExample example);

    int deleteByExample(MqRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MqRecord record);

    int insertSelective(MqRecord record);

    List<MqRecord> selectByExample(MqRecordExample example);

    MqRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MqRecord record, @Param("example") MqRecordExample example);

    int updateByExample(@Param("record") MqRecord record, @Param("example") MqRecordExample example);

    int updateByPrimaryKeySelective(MqRecord record);

    int updateByPrimaryKey(MqRecord record);
}