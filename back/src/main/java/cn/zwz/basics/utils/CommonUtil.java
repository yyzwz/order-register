package cn.zwz.basics.utils;

import cn.zwz.basics.parameter.CommonConstant;
import cn.zwz.basics.exception.ZwzException;
import cn.zwz.data.utils.ZwzNullUtils;
import io.swagger.annotations.ApiOperation;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * 公用工具类
 * @author 郑为中
 */
public class CommonUtil {

    private static SecureRandom random = new SecureRandom();

    @ApiOperation(value = "生成随机文件名称")
    public static String renamePic(String fileName) {
        return UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
    }

    @ApiOperation(value = "生成随机企微验证码")
    public static String getRandomTwoNum() {
        int num = random.nextInt(99);
        // 不足六位前面补0
        String str = String.format("%02d", num);
        return str;
    }

    @ApiOperation(value = "避免删除 DFS 死循环")
    public static Boolean judgeIds(String tempString, String[] list){
        boolean flag = true;
        for(String id : list){
            if(Objects.equals(tempString,id)){
                flag = false;
                break;
            }
        }
        return !flag;
    }

    @ApiOperation(value = "生成随机6位验证码")
    public static String getRandomNum() {
        Random random = new Random();
        int num = random.nextInt(999999);
        return String.format("%06d", num);
    }
}
