/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: DistributionUntil
 * Author:   LiYuAn
 * Date:     2018/7/27 13:22
 * Description: 业务分发工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.donut.ordermq.worker.order;

import cn.donut.ordermq.entity.order.MqOrderInfo;
import cn.donut.ordermq.worker.MqUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈业务分发工具类〉
 *
 * @author LiYuAn
 * @create 2018/7/27
 * @since 1.0.0
 */
@Component
@Aspect
public class DistributionUntil {

    private static final Logger log = LoggerFactory.getLogger(DistributionUntil.class);

    @Autowired
    private MqUtil mqUtil;

    @Pointcut("execution(* cn.donut.ordermq.worker.order.OrderPaySuccessReceiver.pushSuccessAop(..))")
    public void pointCutPaySuccess() {
    }

    //匹配下单和取消订单
    @Pointcut("execution(* cn.donut.ordermq.worker.order.OrderCreateReceiver.pushAop(..)) || " +
            "execution(* cn.donut.ordermq.worker.order.OrderCancelReceiver.pushAop(..))")
    public void pointCut() {
    }

    //支付成功切点
    @Around("pointCutPaySuccess()")
    public void aroundSuccess(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("------------------before-------------------------");
        try {
            System.out.println("------------------around-------------------------");
            Map<String, Object> map = (Map<String, Object>) jp.proceed();
            System.out.println("进入支付成功切点方法");
            if (map.containsKey("productLine")) {
                Integer lineCode = (Integer) map.get("productLine");
                System.out.println(lineCode);
            }
        } catch (Exception e) {
            log.error("异常！", e);
            System.out.println("------------------exception-------------------------");
        }
        System.out.println("------------------after-------------------------");
    }

    //下单切点
    @Around("pointCut()")
    public void aroundCreate(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("------------------before-------------------------");
        try {
            Map<String, Object> map = (Map<String, Object>) jp.proceed();
            System.out.println("进入下单切点方法");
            if (map.containsKey("order")) {
                MqOrderInfo order = (MqOrderInfo) map.get("order");
                Boolean retailm = mqUtil.pushOrderToRetailm(order);
                if (retailm) {
                    log.info("分销系统订单回写成功！订单号：{}", order.getOrderNo());
                } else if (null == retailm) {
                    log.info("不需要回写！订单号：{}");
                } else {
                    log.info("分销系统订单回写失败！订单号：{}", order.getOrderNo());
                }
            }

        } catch (Exception e) {
            log.error("异常！", e);
            System.out.println("------------------exception-------------------------");
        }
        System.out.println("------------------after-------------------------");
    }

}
