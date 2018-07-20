//package cn.donut.ordermq.worker;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
///**
// * @author wangjiahao
// * @date 2018/7/20 9:25
// */
//@Component
//@Aspect
//public class TestAOP {
//
//    private static final Logger log = LoggerFactory.getLogger(TestAOP.class);
//
//    @Pointcut("execution(* cn.donut.ordermq.worker.order.OrderRefundReceiver.*(..))")
//    public void pointcut() {}
//
//
//
//    @Around("pointcut()")
//    public void around(ProceedingJoinPoint jp){
//        System.out.println("=============before================");
//        try {
//            jp.proceed();
//            System.out.println("=============around================");
//        } catch (Throwable throwable) {
//            System.out.println("=============exception================");
//        }
//        System.out.println("=============after================");
//    }
//
//}
