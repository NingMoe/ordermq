/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MqPushFailureServiceImpl
 * Author:   LiYuAn
 * Date:     2018/8/2 10:46
 * Description: 推送失败实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.impl;

import cn.donut.ordermq.entity.MqPushFailure;
import cn.donut.ordermq.entity.MqPushFailureExample;
import cn.donut.ordermq.mapper.MqPushFailureDao;
import cn.donut.ordermq.service.MqPushFailureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈推送失败实现类〉
 *
 * @author LiYuAn
 * @create 2018/8/2
 * @since 1.0.0
 */
public class MqPushFailureServiceImpl implements MqPushFailureService {

    private static final Logger log = LoggerFactory.getLogger(MqPushFailureServiceImpl.class);

    @Autowired
    private MqPushFailureDao mqPushFailureDao;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqPushFailure insert(MqPushFailure mqPushFailure) {
        mqPushFailure.setIsDelete((byte) 0);
        mqPushFailure.setPushTime(new Date());
        mqPushFailure.setPushFlag((byte) 1);
        mqPushFailureDao.insert(mqPushFailure);
        return mqPushFailure;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqPushFailure edit(MqPushFailure mqPushFailure) {
        mqPushFailureDao.updateByPrimaryKeySelective(mqPushFailure);
        return mqPushFailure;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean delete(MqPushFailure mqPushFailure) {
        mqPushFailure.setIsDelete((byte) 1);
        int count = mqPushFailureDao.updateByPrimaryKeySelective(mqPushFailure);
        return count > 0;
    }

    @Override
    public MqPushFailure findOneById(Integer id) {
        return mqPushFailureDao.selectByPrimaryKey(id);
    }

    @Override
    public List<MqPushFailure> findByTarget(String target) {
        MqPushFailureExample example = new MqPushFailureExample();
        example.setOrderByClause("push_time desc");
        example.createCriteria().andPushTargetEqualTo(target).andPushFlagEqualTo((byte) 1).andIsDeleteEqualTo((byte) 0);
        List<MqPushFailure> mqPushFailures = mqPushFailureDao.selectByExample(example);
        return mqPushFailures;
    }

    @Override
    public MqPushFailure insertSelective(MqPushFailure mqPushFailure) {
        mqPushFailure.setIsDelete((byte) 0);
        mqPushFailure.setPushTime(new Date());
        mqPushFailure.setPushFlag((byte) 1);
        mqPushFailureDao.insertSelective(mqPushFailure);
        return mqPushFailure;
    }
}
