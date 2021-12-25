package cn.zwz.modules.base.utils;

/**
 * 判断为空工具类
 * @author 郑为中
 */
public class ZwzNullUtils {
    public static boolean isNull(String str){
        if(str == null || str.equals("") || str.equals("null") || str.equals("undefined")) {
            return true;
        }
        return false;
    }
}
