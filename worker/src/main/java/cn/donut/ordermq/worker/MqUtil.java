/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MqUtil
 * Author:   LiYuAn
 * Date:     2018/7/5 16:09
 * Description: 工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.worker;

import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.retailm.service.common.MsgEncryptionService;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParseException;
import com.koolearn.ordercenter.model.OrderDistributionInfo;
import com.koolearn.ordercenter.service.IOrderDistributionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 〈一句话功能简述〉<br>
 * 〈工具类〉
 *
 * @author LiYuAn
 * @create 2018/7/5
 * @since 1.0.0
 */
@Slf4j
public class MqUtil {

    @Autowired
    private IOrderDistributionInfoService iOrderDistributionInfoService;

    @Autowired
    private MsgEncryptionService msgEncryptionService;


    public MqOrderInfo Json2Order(String json) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            String orderNo = jsonObject.get("orderNo").toString();
            Integer userId = (Integer) jsonObject.get("userId");

            MqOrderInfo orderInfo = new MqOrderInfo();
            orderInfo.setOrderNo(orderNo);
            orderInfo.setUserId(userId);

            return orderInfo;
        } catch (JsonParseException e) {
            log.error("JSON格式有误！", e);
        } catch (NullPointerException e) {
            log.error("JSON缺少关键字！", e);
        } catch (Exception e) {
            log.error("其他异常！", e);
        }
        return null;
    }

    public Integer getRetailMemberId(MqOrderInfo mqOrderInfo) {
        OrderDistributionInfo orderDistributionInfo = iOrderDistributionInfoService.findOrderDistributionInfoByOrderNo(mqOrderInfo.getOrderNo());
        if (null != orderDistributionInfo) {
            //解密分销员id
            String id = null;
            try {
                id = msgEncryptionService.decryption(orderDistributionInfo.getDistributionUser());
                System.out.println("分销员id=" + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Integer.valueOf(id);
        }
        return null;
    }
}