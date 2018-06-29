/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MqRecordServiceImpl
 * Author:   LiYuAn
 * Date:     2018/6/28 18:48
 * Description: mq消息记录实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.impl.order;

import cn.donut.ordermq.entity.MqRecord;
import cn.donut.ordermq.mapper.MqRecordMapper;
import cn.donut.ordermq.service.MqRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈mq消息记录实现类〉
 *
 * @author LiYuAn
 * @create 2018/6/28
 * @since 1.0.0
 */
@Service
public class MqRecordServiceImpl implements MqRecordService {

    @Autowired
    private MqRecordMapper mqRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqRecord insert(MqRecord mqRecord) {
        mqRecord.setCreateTime(new Date());
        int i = mqRecordMapper.insert(mqRecord);
        return i > 0 ? mqRecord : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqRecord edit(MqRecord mqRecord) {
        mqRecordMapper.updateByPrimaryKey(mqRecord);
        return mqRecord;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MqRecord delete(MqRecord mqRecord) {
        return null;
    }

    @Override
    public MqRecord findOneById(Integer id) {
        MqRecord mqRecord = mqRecordMapper.selectByPrimaryKey(id);
        return mqRecord;
    }

    @Override
    public List<MqRecord> findMqRecords() {
        return null;
    }
}
