package cn.zwz.basics.redis;

import io.swagger.annotations.ApiModelProperty;

import java.lang.annotation.*;

/**
 * 限流注解
 * @author 郑为中
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    @ApiModelProperty(value = "单位时间限流个数")
    int limit() default 1000;

    @ApiModelProperty(value = "单位时间毫秒数")
    long timeout() default 60000;
}
