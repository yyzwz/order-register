package cn.zwz.data.utils;

import java.util.Objects;

/**
 * 判断为空工具类
 * @author 郑为中
 */
public class ZwzNullUtils {
    public static boolean isNull(String str){
        if(str == null || Objects.equals("",str) || Objects.equals("null",str) || Objects.equals("undefined",str)) {
            return true;
        }
        return false;
    }
}
