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

import cn.donut.ordermq.entity.MqInformation;
import cn.donut.ordermq.entity.MqInformationExample;
import cn.donut.ordermq.mapper.MqInformationDao;
import cn.donut.ordermq.service.MqInformationServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 〈MqInformation查询实现类〉
 *
 * @author LiYuAn
 * @create 2018/5/15
 * @since 1.0.0
 */
public class MqInformationServiceProviderImpl implements MqInformationServiceProvider {

    @Autowired
    private MqInformationDao mqInformationDao;

    //根据ID获取实体
    @Override
    public MqInformation getMqInformationById(int id) {

        return this.mqInformationDao.selectByPrimaryKey(id);
    }

    //新增
    @Override
    public int insertMqInformation(MqInformation mqInformation) {

        return this.mqInformationDao.insert(mqInformation);
    }

    //修改
    @Override
    public void updateMqInformation(MqInformation mqInformation) {
        this.mqInformationDao.updateByPrimaryKeySelective(mqInformation);
    }


}
