package cn.zwz.common.annotation;

import java.lang.annotation.*;

/**
 * 限流注解
 * @author 郑为中
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    int limit() default 5;

    long timeout() default 1000;
}
