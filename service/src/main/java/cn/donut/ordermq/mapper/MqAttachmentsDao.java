package cn.donut.ordermq.mapper;

import cn.donut.ordermq.entity.MqAttachments;
import cn.donut.ordermq.entity.MqAttachmentsExample;
import com.koolearn.framework.mybatis.annotation.DAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DAO
public interface MqAttachmentsDao {
    int countByExample(MqAttachmentsExample example);

    int deleteByExample(MqAttachmentsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MqAttachments record);

    int insertSelective(MqAttachments record);

    List<MqAttachments> selectByExample(MqAttachmentsExample example);

    MqAttachments selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MqAttachments record, @Param("example") MqAttachmentsExample example);

    int updateByExample(@Param("record") MqAttachments record, @Param("example") MqAttachmentsExample example);

    int updateByPrimaryKeySelective(MqAttachments record);

    int updateByPrimaryKey(MqAttachments record);
}