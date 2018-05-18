/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MqInformationProvider
 * Author:   LiYuAn
 * Date:     2018/5/15 18:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.service;

import cn.donut.ordermq.entity.MqInformation;

/**
 * 〈mq信息〉
 *
 * @author LiYuAn
 * @create 2018/5/15
 * @since 1.0.0
 */
public interface MqInformationServiceProvider {

    //根据ID获取实体
    MqInformation getMqInformationById(int id);

    //新增
    MqInformation insertMqInformation(MqInformation mqInformation);

    //修改
    void updateMqInformation(MqInformation mqInformation);
}
