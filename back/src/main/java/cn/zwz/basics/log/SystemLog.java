package cn.zwz.basics.log;

import io.swagger.annotations.ApiModelProperty;

import java.lang.annotation.*;

/**
 * 日志注解
 * @author 郑为中
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

        @ApiModelProperty(value = "日志名称")
        public String about() default "系统日志";

        @ApiModelProperty(value = "日志类型")
        public LogType type() default LogType.DEFAULT_OPERATION;

        @ApiModelProperty(value = "操作代码")
        String doType() default "";
}
