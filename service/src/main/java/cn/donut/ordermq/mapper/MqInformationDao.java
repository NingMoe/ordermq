package cn.donut.ordermq.mapper;

import cn.donut.ordermq.entity.MqInformation;
import cn.donut.ordermq.entity.MqInformationExample;
import com.koolearn.framework.mybatis.annotation.DAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DAO
public interface MqInformationDao {

    public int insert(MqInformation record);

    public List<MqInformation> selectByExample(MqInformationExample example);

    public MqInformation selectByPrimaryKey(Integer id);

    public int updateByExampleSelective(@Param("record") MqInformation record, @Param("example") MqInformationExample example);

    public int updateByExample(@Param("record") MqInformation record, @Param("example") MqInformationExample example);

    public int updateByPrimaryKeySelective(MqInformation record);

    public int updateByPrimaryKey(MqInformation record);
}