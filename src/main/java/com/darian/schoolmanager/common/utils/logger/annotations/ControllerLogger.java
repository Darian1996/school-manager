package com.darian.schoolmanager.common.utils.logger.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ControllerLogger {

    /**
     * 是否打印参数
     *
     * @return
     */
    boolean needParams() default true;

    /**
     * 是否打印结果
     *
     * @return
     */
    boolean needResult() default true;
}