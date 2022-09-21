package cn.zwz.basics.utils;

import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Response 回调前端
 * @author 郑为中
 */
@Slf4j
public class ResponseUtil {

    private static final String CHARACTER_ENCODING = "UTF-8";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @ApiModelProperty(value = "输出JSON")
    public static void out(HttpServletResponse httpServletResponse, Map<String, Object> responseMap){
        ServletOutputStream outputStream = null;
        try {
            httpServletResponse.setCharacterEncoding(CHARACTER_ENCODING);
            httpServletResponse.setContentType(CONTENT_TYPE);
            outputStream = httpServletResponse.getOutputStream();
            outputStream.write(new Gson().toJson(responseMap).getBytes());
        } catch (Exception exception) {
            log.warn(exception + "Response转换JSON错误");
        } finally{
            if(outputStream != null){
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Map<String, Object> resultMap(boolean flag, Integer responseCode, String message, Object data){
        Map<String, Object> responseMap = new HashMap<String, Object>(16);
        responseMap.put("result", data == null ? null : data);
        responseMap.put("timestamp", System.currentTimeMillis() / 1000L);
        responseMap.put("success", flag);
        responseMap.put("code", responseCode);
        responseMap.put("message", message);
        return responseMap;
    }

    public static Map<String, Object> resultSuccessMap(String msg){
        return resultMap(true, 200, msg, null);
    }

    public static Map<String, Object> resultMap(boolean successFlag, Integer code, String msg){
        return resultMap(successFlag, code, msg, null);
    }

    public static Map<String, Object> resultSuccessMap(){
        return resultMap(true, 200, "操作成功", null);
    }
}
