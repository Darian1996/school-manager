package com.darian.schoolmanager.common.utils.logger;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.Resource;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2020/12/3  22:56
 */
@Aspect
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoggerConfiguration {

    //@Pointcut("execution(* com.darian.schoolmanager.*.controller..*(..))")
    //public void controllerMethod() {
    //}

    //@Around(value = "controllerMethod()")
    //@AdviceName("controllerAspectName")
    //public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    //    ((MethodInvocationProceedingJoinPoint) proceedingJoinPoint).methodInvocation;
    //    return controllerLoggerInterceptor.invoke((MethodInvocation) proceedingJoinPoint);
    //    //return controllerLoggerInterceptor.invoke(proceedingJoinPoint);
    //}

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ControllerLoggerInterceptor controllerLoggerInterceptor;

    @Resource
    private AspectJExpressionPointcut controllerLoggerPointcut;

    @Bean
    public ControllerLoggerInterceptor controllerLoggerInterceptor() {
        ControllerLoggerInterceptor controllerLoggerInterceptor = new ControllerLoggerInterceptor();
        controllerLoggerInterceptor.setApplicationContext(applicationContext);
        return controllerLoggerInterceptor;
    }

    @Bean
    public AspectJExpressionPointcut controllerLoggerPointcut() {
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(
                "execution(* com.darian.schoolmanager.*.controller..*(..)) and "
                        + "(@annotation(com.darian.schoolmanager.common.utils.logger.annotations.ControllerLogger) "
                        + "or  @within(com.darian.schoolmanager.common.utils.logger.annotations.ControllerLogger))");
        return aspectJExpressionPointcut;
    }

    @Bean
    public DefaultPointcutAdvisor controllerLoggerPointcutAdvisor() {
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
        defaultPointcutAdvisor.setPointcut(controllerLoggerPointcut);
        defaultPointcutAdvisor.setAdvice(controllerLoggerInterceptor);
        return defaultPointcutAdvisor;
    }

    @Resource
    private ServiceLogInterceptor serviceLogInterceptor;

    @Resource
    private AspectJExpressionPointcut serviceLoggerPointcut;

    @Bean
    public ServiceLogInterceptor serviceLogInterceptor() {
        ServiceLogInterceptor serviceLogInterceptor = new ServiceLogInterceptor();
        serviceLogInterceptor.setApplicationContext(applicationContext);
        return serviceLogInterceptor;
    }

    @Bean
    public AspectJExpressionPointcut serviceLoggerPointcut() {
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression("execution(* com.darian.schoolmanager.*.service..*(..)) and "
                + "(@annotation(com.darian.schoolmanager.common.utils.logger.annotations.ServiceLogger) "
                + "or  @within(com.darian.schoolmanager.common.utils.logger.annotations.ServiceLogger))");
        return aspectJExpressionPointcut;
    }

    @Bean
    public DefaultPointcutAdvisor serviceLoggerPointcutAdvisor() {
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
        defaultPointcutAdvisor.setPointcut(serviceLoggerPointcut);
        defaultPointcutAdvisor.setAdvice(serviceLogInterceptor);
        return defaultPointcutAdvisor;
    }

}