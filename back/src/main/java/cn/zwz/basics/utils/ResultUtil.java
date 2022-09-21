package cn.zwz.basics.utils;

import cn.zwz.basics.baseVo.Result;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 郑为中
 */
public class ResultUtil<T> {

    private Result<T> result;

    private static final String DEFAULR_SUCCESS_STR = "OK";

    private static final String DEFAULR_FAIL_STR = "操作失败";

    public ResultUtil(){
        result = new Result<>();
        result.setSuccess(true);
        result.setMessage(DEFAULR_SUCCESS_STR);
        result.setCode(200);
    }

    @ApiModelProperty(value = "成功返回数据")
    public Result<T> setData(T t){
        this.result.setResult(t);
        this.result.setCode(200);
        return this.result;
    }

    @ApiModelProperty(value = "成功返回数据和备注")
    public Result<T> setDataAndMessage(T t, String msg){
        this.result.setResult(t);
        this.result.setCode(200);
        this.result.setMessage(msg);
        return this.result;
    }

    @ApiModelProperty(value = "成功返回备注")
    public Result<T> setSuccessMsg(String msg){
        this.result.setSuccess(true);
        this.result.setMessage(msg);
        this.result.setCode(200);
        this.result.setResult(null);
        return this.result;
    }

    @ApiModelProperty(value = "成功返回数据和备注")
    public Result<T> setData(T t, String msg){
        this.result.setResult(t);
        this.result.setCode(200);
        this.result.setMessage(msg);
        return this.result;
    }

    @ApiModelProperty(value = "错误返回备注")
    public Result<T> setErrorMsg(String msg){
        this.result.setSuccess(false);
        this.result.setMessage(msg);
        this.result.setCode(500);
        return this.result;
    }

    @ApiModelProperty(value = "错误返回状态码备注")
    public Result<T> setErrorMsg(Integer code, String msg){
        this.result.setSuccess(false);
        this.result.setMessage(msg);
        this.result.setCode(code);
        return this.result;
    }

    public static <T> Result<T> data(T t){
        return new ResultUtil<T>().setData(t);
    }

    public static <T> Result<T> data(){
        return new ResultUtil<T>().setData(null);
    }

    public static <T> Result<T> data(T t, String msg){
        return new ResultUtil<T>().setData(t, msg);
    }

    public static <T> Result<T> error(String msg){
        return new ResultUtil<T>().setErrorMsg(msg);
    }

    public static <T> Result<T> error(){
        return new ResultUtil<T>().setErrorMsg(DEFAULR_FAIL_STR);
    }

    public static <T> Result<T> error(Integer code, String msg){
        return new ResultUtil<T>().setErrorMsg(code, msg);
    }

    public static <T> Result<T> success(String msg){
        return new ResultUtil<T>().setSuccessMsg(msg);
    }

    public static <T> Result<T> success(){
        return new ResultUtil<T>().setSuccessMsg(DEFAULR_SUCCESS_STR);
    }
}
