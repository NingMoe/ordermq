/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MqPushFailureService
 * Author:   LiYuAn
 * Date:     2018/8/2 10:44
 * Description: 推送失败实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.service;

import cn.donut.ordermq.entity.MqPushFailure;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈推送失败服务类〉
 *
 * @author LiYuAn
 * @create 2018/8/2
 * @since 1.0.0
 */
public interface MqPushFailureService {

    MqPushFailure insert(MqPushFailure mqPushFailure);

    MqPushFailure edit(MqPushFailure mqPushFailure);

    Boolean delete(MqPushFailure mqPushFailure);

    MqPushFailure findOneById(Integer id);

    List<MqPushFailure> findByTarget(String target);

    MqPushFailure insertSelective(MqPushFailure mqPushFailure);

}
