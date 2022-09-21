package cn.zwz.basics.exception;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * InternalAuthenticationServiceException 异常处理类
 * @author 郑为中
 */
public class ZwzAuthException extends InternalAuthenticationServiceException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MSG = "系统鉴权失败";

    @ApiModelProperty(value = "异常消息内容")
    private String msg;

    public ZwzAuthException(String msg){
        super(msg);
        this.msg = msg;
    }

    public ZwzAuthException(){
        super(DEFAULT_MSG);
        this.msg = DEFAULT_MSG;
    }

    public ZwzAuthException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
