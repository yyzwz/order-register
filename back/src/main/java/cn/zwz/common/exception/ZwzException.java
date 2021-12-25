package cn.zwz.common.exception;

import lombok.Data;

/**
 * @author 郑为中
 */
@Data
public class ZwzException extends RuntimeException {

    private String msg;

    public ZwzException(String msg){
        super(msg);
        this.msg = msg;
    }
}
