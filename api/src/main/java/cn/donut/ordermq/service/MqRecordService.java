/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MqRecordService
 * Author:   LiYuAn
 * Date:     2018/6/28 18:41
 * Description: mq消息记录
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.service;

import cn.donut.ordermq.entity.MqRecord;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈mq消息记录〉
 *
 * @author LiYuAn
 * @create 2018/6/28
 * @since 1.0.0
 */
public interface MqRecordService {

    MqRecord insert(MqRecord mqRecord);

    MqRecord edit(MqRecord mqRecord);

    MqRecord delete(MqRecord mqRecord);

    MqRecord findOneById(Integer id);

    List<MqRecord> findMqRecords();

}
