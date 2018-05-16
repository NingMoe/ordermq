/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MqAttachmentsServiceProvider
 * Author:   LiYuAn
 * Date:     2018/5/16 9:49
 * Description: 附加信息查询接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.service;

import cn.donut.ordermq.entity.MqAttachments;

import java.util.List;

/**
 * 〈附加信息查询接口〉
 *
 * @author LiYuAn
 * @create 2018/5/16
 * @since 1.0.0
 */
public interface MqAttachmentsServiceProvider {

    //根据id获取实体
    public MqAttachments getMqAttachmentsById(int id);

    //根据mqInformation主键获取实体
    public List<MqAttachments> getMqAttachmentsByMqId(int mqId);

    //新增
    public void insertMqAttachments(MqAttachments mqAttachments);

}