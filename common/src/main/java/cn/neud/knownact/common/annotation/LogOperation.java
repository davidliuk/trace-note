package cn.neud.knownact.common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author David l729641074@163.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {

	String value() default "";
}
