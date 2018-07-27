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

import cn.donut.crm.service.SystemAllocationService;
import cn.donut.ordermq.service.order.IOrderProductService;
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
    private IOrderProductService iOrderProductService;

    @Autowired
    private SystemAllocationService systemAllocationService;

    @Pointcut("execution(* cn.donut.ordermq.worker.order.OrderPaySuccessReceiver.pushSuccessAop(..))")
    public void pointCutPaySuccess() {
    }

    @Pointcut("execution(* cn.donut.ordermq.worker.order.OrderCreateReceiver.pushAopCreate(..))")
    public void pointCutCreate() {
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
    @Around("pointCutCreate()")
    public void aroundCreate(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("------------------before-------------------------");
        try {
            System.out.println("------------------around-------------------------");
            Map<String, Object> map = (Map<String, Object>) jp.proceed();
            System.out.println("进入下单切点方法");
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

}
