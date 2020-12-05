package com.darian.schoolmanager.common.utils.logger;

import com.darian.schoolmanager.common.modle.CustomerResponse;
import com.darian.schoolmanager.common.utils.JSONUtils;
import com.darian.schoolmanager.common.utils.logger.DO.TraceLoggerModuleDO;
import com.darian.schoolmanager.common.utils.logger.annotations.ControllerLogger;
import com.darian.schoolmanager.common.utils.logger.service.TraceLoggerService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class BaseLogInterceptor implements MethodInterceptor {

    private Logger LOGGER = LoggerFactory.getLogger(BaseLogInterceptor.class);

    /**
     * 日志默认值
     */
    protected static final String LOG_DEFAULT = "-";

    /**
     * 日志前缀
     */
    protected static final String LOG_PREFIX = "[";

    /**
     * 日志后缀
     */
    protected static final String LOG_SUFFIX = "]";

    /**
     * 日志参数前缀
     */
    protected static final String LOG_PARAM_PREFIX = "(";

    /**
     * 日志参数后缀
     */
    protected static final String LOG_PARAM_SUFFIX = ")";

    /**
     * 日志分隔符(英文分号)
     */
    protected static final String LOG_SEP = ",";

    /**
     * 日志分隔符(英文点号)
     */
    protected static final String LOG_SEP_POINT = ".";

    /**
     * 成功
     */
    protected static final String YES = "Y";

    /**
     * 失败
     */
    protected static final String NO = "N";

    /**
     * 时间
     */
    protected static final String TIME_UNIT = "ms";

    private ApplicationContext applicationContext;


    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long startTime = System.currentTimeMillis();

        Method method = methodInvocation.getMethod();

        String classSimpleName = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();

        ControllerLogger controllerLogger = methodInvocation.getMethod().getAnnotation(ControllerLogger.class);

        // 获取参数
        Object[] args = methodInvocation.getArguments();
        //调用是否成功
        boolean isSuccess = true;
        //拦截方法的执行结果
        Object result = null;

        try {
            result = methodInvocation.proceed();
            isSuccess = isSuccess(result);
            return result;
        } catch (Throwable t) {
            isSuccess = false;
            throw t;
        } finally {

            // 确保任何情况下业务都能正常进行
            try {
                long endTime = System.currentTimeMillis();
                long costTimes = endTime - startTime;
                // TODO:
                LOGGER.info(defaultConstructLogString(classSimpleName, methodName, isSuccess, costTimes, args, result, controllerLogger));
            } catch (Exception e) {
                LOGGER.error("[LOGGER][Controller][msg]", e);
            }
        }
    }

    private boolean isSuccess(Object result) {
        if (result == null) {
            return false;
        } else {
            if (result instanceof CustomerResponse<?>) {
                return "0".equals(((CustomerResponse<?>) result).getCode());
            }
        }
        return true;
    }

    protected final String getTraceId() {
        String traceIdValue = MDC.get("traceId");
        StringBuilder sb = new StringBuilder();
        sb.append(isBlankGetDefault(traceIdValue, LOG_DEFAULT));
        return sb.toString();
    }

    protected final String getMsgOfArgs(Object[] objects) {

        if (objects == null) {
            return LOG_PARAM_PREFIX + LOG_DEFAULT + LOG_PARAM_SUFFIX;
        }

        StringBuffer sb = new StringBuffer();

        sb.append(LOG_PARAM_PREFIX);

        for (int i = 0; i < objects.length; i++) {

            if (objects[i] != null && objects[i].getClass().isAnonymousClass()) {
                //如果是匿名类
                sb.append(getMsgOfAnonymous(objects[i]));
            } else {
                sb.append(getMsg(objects[i]));
            }

            //拼接逗号
            if (i != objects.length - 1) {
                sb.append(LOG_SEP);
            }
        }

        sb.append(LOG_PARAM_SUFFIX);

        return sb.toString();
    }

    protected final String getMsgOfAnonymous(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder anonymousSB = new StringBuilder();
        anonymousSB.append("[");
        for (Field field : fields) {
            try {
                //过滤掉父类的应用
                if ("this$0".equals(field.getName()) || "$jacocoData".equals(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                Object obj = field.get(object);
                if (obj != null) {
                    anonymousSB.append(field.getName())
                            .append(":")
                            .append(getSimpleMsg(obj))
                            .append(";");
                }
            } catch (Exception e) {
                LOGGER.error("getMsgOfAnonymous error" + field.getName(), e);
            }
        }
        anonymousSB.append("]");
        return anonymousSB.toString();
    }

    protected final String getSimpleMsg(Object object) {
        if (object == null) {
            return LOG_DEFAULT;
        } else if (object.getClass() == String.class
                || object.getClass() == Integer.class
                || object.getClass() == Long.class
                || object.getClass() == Double.class) {
            //基本类型直接输出
            return object.toString();
        } else if (object.getClass().isArray()) {
            return Arrays.asList(object).toString();
        } else {
            //复杂入参不打印
            return LOG_DEFAULT;
        }
    }

    protected static String getMsg(Object object) {

        if (object == null) {
            return LOG_DEFAULT;
        } else if (object.getClass() == String.class
                || object.getClass() == Integer.class
                || object.getClass() == Long.class
                || object.getClass() == Double.class) {
            return object.toString();
        } else if (object instanceof List<?>) {
            return object.toString();
        } else if (object.getClass().isArray()) {
            return Arrays.asList(object).toString();
        } else if (object instanceof Map) {
            return JSONUtils.beanToJson(object);
        } else {
            return JSONUtils.beanToJson(object);
        }
    }

    public static String isBlankGetDefault(String str, String defaultValue) {
        if (str == null || str.length() == 0) {
            return defaultValue;
        }
        return str;
    }

    protected final String defaultConstructLogString(String classSimpleName, String methodName, boolean isSuccess,
                                                     long costTimes, Object[] args, Object result, ControllerLogger controllerLogger) {

        TraceLoggerModuleDO traceLoggerModuleDO = builderTraceLoggerModuleDO(classSimpleName, methodName, isSuccess, costTimes, args,
                result, controllerLogger);
        StringBuilder sb = new StringBuilder();
        sb.append(LOG_PARAM_PREFIX);
        sb.append(traceLoggerModuleDO.getTraceId());
        sb.append(LOG_PARAM_SUFFIX);

        sb.append(LOG_PREFIX);
        sb.append(LOG_PARAM_PREFIX);
        sb.append(traceLoggerModuleDO.getClassSimpleName());
        sb.append(LOG_SEP_POINT);
        sb.append(traceLoggerModuleDO.getMethodName());
        sb.append(LOG_SEP);
        sb.append(traceLoggerModuleDO.getSuccess());
        sb.append(LOG_SEP);
        sb.append(traceLoggerModuleDO.getCostTimes());
        sb.append(TIME_UNIT);
        sb.append(LOG_PARAM_SUFFIX);

        // 参数已经有 "()" 号了
        sb.append(traceLoggerModuleDO.getArgsMsg());

        sb.append(LOG_PARAM_PREFIX);
        sb.append(traceLoggerModuleDO.getResultMsg());
        sb.append(LOG_PARAM_SUFFIX);

        sb.append(LOG_SUFFIX);

        return sb.toString().replaceAll("\n", "");
    }

    private TraceLoggerModuleDO builderTraceLoggerModuleDO(String classSimpleName, String methodName, boolean isSuccess,
                                                           long costTimes, Object[] args, Object result, ControllerLogger controllerLogger) {
        TraceLoggerModuleDO traceLoggerModuleDO = new TraceLoggerModuleDO();
        traceLoggerModuleDO.setTraceId(getTraceId());
        traceLoggerModuleDO.setClassSimpleName(classSimpleName);
        traceLoggerModuleDO.setMethodName(methodName);
        traceLoggerModuleDO.setSuccess(isSuccess ? YES : NO);
        traceLoggerModuleDO.setCostTimes(costTimes);
        // 默认打印
        if (controllerLogger == null || controllerLogger.needParams()) {
            traceLoggerModuleDO.setArgsMsg(getMsgOfArgs(args));
        }
        // 默认打印
        if (controllerLogger == null || controllerLogger.needResult()) {
            traceLoggerModuleDO.setResultMsg(getMsg(result));
        }

        traceLoggerModuleDO.setLoggerTime(LocalDateTime.now());

        applicationContext.publishEvent(new LogApplicationEvent(traceLoggerModuleDO));

        return traceLoggerModuleDO;
    }

}