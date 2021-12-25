package cn.zwz.common.exception;

import lombok.Data;

/**
 * @author 郑为中
 */
@Data
public class CaptchaException extends RuntimeException {

    private String msg;

    public CaptchaException(String msg){
        super(msg);
        this.msg = msg;
    }
}
