package com.rokid.skill.kit4j.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


/**
 * 函数出入口的日志打印以及执行时间打印
 *
 * @author wuyukai
 */
@Slf4j
@Aspect
@Component
public class MethodLogger {

    /**
     * 切面定义，注解及包名检验切面
     */
    @Pointcut("within(com.rokid.skill..*) && @annotation(com.rokid.skill.kit4j.aop.Loggable)")
    public void pointcut() {
    }

    /**
     * 打印方法参数
     *
     * @param joinPoint 切面
     */
    @Before(value = "pointcut()")
    public void logMethodInvokeParam(JoinPoint joinPoint) {
        log.info("Before method {} invoke, param: {}", joinPoint.getSignature().toShortString(),
            joinPoint.getArgs());
    }

    /**
     * 打印返回值
     *
     * @param joinPoint 切面
     * @param retVal 返回值
     */
    @AfterReturning(value = "pointcut()", returning = "retVal")
    public void logMethodInvokeResult(JoinPoint joinPoint, Object retVal) {
        log.info("After method {} invoke, result: {}", joinPoint.getSignature().toShortString(),
            retVal);
    }

    /**
     * 异常信息打印
     *
     * @param joinPoint 切面
     * @param exception 异常信息
     */
    @AfterThrowing(value = "pointcut() ", throwing = "exception")
    public void logMethodInvokeException(JoinPoint joinPoint,
        Exception exception) {
        log.info("method {} invoke exception: {}", joinPoint.getSignature().toShortString(),
            exception);
    }

    /**
     * 打印方法执行耗时
     *
     * @param pjp 切面
     * @return 返回值
     * @throws Throwable 异常
     */
    @Around(value = "pointcut()")
    public Object methodInvokeExpiredTime(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retVal = pjp.proceed();
        stopWatch.stop();
        log.info("method {} invoked, expired time = {} ms", pjp.getSignature().toShortString(),
            stopWatch.getTotalTimeMillis());
        return retVal;
    }


}
