package cn.zwz.basics.redis;

import cn.hutool.core.util.StrUtil;
import cn.zwz.data.utils.ZwzNullUtils;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 限流算法
 * @author 郑为中
 */
@Slf4j
@Component
public class RedisRaterLimiter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String INIT_TIMES = "1";

    private static final String LIMIT_MAX_PRE = "ZWZ_LIMIT_MAX:";

    private static final String LIMIT_NOW_PRE = "ZWZ_LIMIT_NOW:";

    public boolean getLimitFlag(@ApiParam(name = "IP地址等唯一标识") String point, @ApiParam(name = "限流次数") Integer limit, @ApiParam(name = "限流单位时间") Long timeout) {
        if(limit < 1 ||timeout < 1){
            return true;
        }
        String maxCountKey = LIMIT_MAX_PRE + point;
        String currCountKey = LIMIT_NOW_PRE + point;
        try {
            // 限流次数
            String maxCount = redisTemplate.opsForValue().get(maxCountKey);
            // 当前消耗次数
            String currCount = redisTemplate.opsForValue().get(currCountKey);
            if(ZwzNullUtils.isNull(maxCount)){
                // 重置次数
                redisTemplate.opsForValue().set(currCountKey, INIT_TIMES, timeout, TimeUnit.MILLISECONDS);
                // 放入限制次数
                redisTemplate.opsForValue().set(maxCountKey, limit + "", timeout, TimeUnit.MILLISECONDS);
                return true;
            } else if (!ZwzNullUtils.isNull(maxCount) && !ZwzNullUtils.isNull(currCount)){
                if(Integer.valueOf(currCount) < Integer.valueOf(maxCount)){
                    redisTemplate.opsForValue().set(currCountKey, (Integer.valueOf(currCount) + 1) + "", timeout, TimeUnit.MILLISECONDS);
                    return true;
                }
            }
            return true;
        } catch (Exception e) {
            log.warn("限流异常" + e);
        }
        return false;
    }
}

