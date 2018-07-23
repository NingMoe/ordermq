package cn.donut.ordermq.worker;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wangjiahao
 * @date 2018/7/19 13:55
 */
@Component
@Aspect
public class TestAop {

    private static final Logger log = LoggerFactory.getLogger(TestAop.class);

    @Pointcut("execution(* cn.donut.ordermq.worker.order.OrderRefundReceiver.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("------------------before-------------------------");
        try {
            System.out.println("------------------around-------------------------");
            jp.proceed();
        } catch (Exception e) {
            log.error("异常！", e);
            System.out.println("------------------exception-------------------------");
        }
        System.out.println("------------------after-------------------------");
    }

}