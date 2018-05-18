/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MqInformationServiceProviderImpl
 * Author:   LiYuAn
 * Date:     2018/5/15 19:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.impl;

import cn.donut.ordermq.entity.MqAttachments;
import cn.donut.ordermq.entity.MqInformation;
import cn.donut.ordermq.entity.MqInformationExample;
import cn.donut.ordermq.mapper.MqAttachmentsDao;
import cn.donut.ordermq.mapper.MqInformationDao;
import cn.donut.ordermq.service.MqInformationServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈MqInformation查询实现类〉
 *
 * @author LiYuAn
 * @create 2018/5/15
 * @since 1.0.0
 */
public class MqInformationServiceProviderImpl implements MqInformationServiceProvider {

    private static final Logger log = LoggerFactory.getLogger(MqInformationServiceProviderImpl.class);

    @Autowired
    private MqInformationDao mqInformationDao;
    @Autowired
    private MqAttachmentsDao mqAttachmentsDao;

    //根据ID获取实体
    @Override
    public MqInformation getMqInformationById(int id) {
        return this.mqInformationDao.selectByPrimaryKey(id);
    }

    //新增
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void insertMqInformation(MqInformation mqInformation) {
        saveMqInfor(mqInformation);
        //附加信息部分
        HashMap<String, String> attachemts = mqInformation.getAttachments();
        if (attachemts != null) {
            saveAttachments(mqInformation,attachemts);
        }
    }


    //保存json中mq消息实体
    private MqInformation saveMqInfor(MqInformation mqInformation) {

        Date date = new Date();
        mqInformation.setIsPulish((byte) 0);
        mqInformation.setCreateTime(date);
        mqInformation.setIsDelete((byte) 0);
        mqInformation.setFailCount(0);
        mqInformationDao.insert(mqInformation);
        log.info("Message has been saved！ID===>:{}", mqInformation.getId());
        System.out.println("Message has been saved！ID===>:{}" + mqInformation.getId());

        return mqInformation;
    }

    //保存附加信息
    private void saveAttachments(MqInformation mqInformation, HashMap<String, String> attachemts) {
        Date date = new Date();
        for (Map.Entry<String, String> entry : attachemts.entrySet()) {
            MqAttachments mqAttachments = new MqAttachments();
            System.out.println(entry.getKey() + ":" + entry.getValue());
            mqAttachments.setTheKey(entry.getKey());
            mqAttachments.setTheValue(entry.getValue());
            mqAttachments.setMqInformId(mqInformation.getId());
            mqAttachments.setCreateTime(date);
            mqAttachments.setIsDelete((byte) 0);
            mqAttachmentsDao.insert(mqAttachments);
            log.info("Attachments has been saved！ID===>:{}", mqAttachments.getId());
        }
    }

    //修改
    @Override
    public void updateMqInformation(MqInformation mqInformation) {
        this.mqInformationDao.updateByPrimaryKeySelective(mqInformation);
    }


}
