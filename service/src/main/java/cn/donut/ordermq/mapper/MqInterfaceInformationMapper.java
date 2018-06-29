package cn.donut.ordermq.mapper;

import cn.donut.ordermq.entity.MqInterfaceInformation;
import cn.donut.ordermq.entity.MqInterfaceInformationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MqInterfaceInformationMapper {
    int countByExample(MqInterfaceInformationExample example);

    int deleteByExample(MqInterfaceInformationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MqInterfaceInformation record);

    int insertSelective(MqInterfaceInformation record);

    List<MqInterfaceInformation> selectByExample(MqInterfaceInformationExample example);

    MqInterfaceInformation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MqInterfaceInformation record, @Param("example") MqInterfaceInformationExample example);

    int updateByExample(@Param("record") MqInterfaceInformation record, @Param("example") MqInterfaceInformationExample example);

    int updateByPrimaryKeySelective(MqInterfaceInformation record);

    int updateByPrimaryKey(MqInterfaceInformation record);
}