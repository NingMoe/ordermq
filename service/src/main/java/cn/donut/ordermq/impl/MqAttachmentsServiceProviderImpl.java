/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MqAttachmentsServiceProviderImpl
 * Author:   LiYuAn
 * Date:     2018/5/16 9:52
 * Description: MqAttachments查询实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.impl;

import cn.donut.ordermq.entity.MqAttachments;
import cn.donut.ordermq.entity.MqAttachmentsExample;
import cn.donut.ordermq.mapper.MqAttachmentsDao;
import cn.donut.ordermq.service.MqAttachmentsServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 〈MqAttachments查询实现类〉
 *
 * @author LiYuAn
 * @create 2018/5/16
 * @since 1.0.0
 */
public class MqAttachmentsServiceProviderImpl implements MqAttachmentsServiceProvider {

    @Autowired
    private MqAttachmentsDao mqAttachmentsDao;

    //根据id获取实体
    @Override
    public MqAttachments getMqAttachmentsById(int id) {
        return this.mqAttachmentsDao.selectByPrimaryKey(id);
    }

    //根据mqInformation主键获取实体
    @Override
    public List<MqAttachments> getMqAttachmentsByMqId(int mqId) {
        MqAttachmentsExample mqAttachmentsExample = new MqAttachmentsExample();
        mqAttachmentsExample.createCriteria().andMqInformIdEqualTo(mqId);

        return this.mqAttachmentsDao.selectByExample(mqAttachmentsExample);
    }

    //新增
    @Override
    public void insertMqAttachments(MqAttachments mqAttachments) {
        this.mqAttachmentsDao.insert(mqAttachments);
    }
}
