package cn.zwz.basics.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Objects;

/**
 * Redis异常处理
 * @author 郑为中
 */
@Configuration
@Slf4j
public class RedisExceptionThrowsConfig extends CachingConfigurerSupport {

    @ApiModelProperty(value = "时长类型")
    private String unit = "day";

    @ApiModelProperty(value = "时长值", notes = "-1为不限制")
    private Integer time = 30;

    @Override
    @ApiOperation(value = "Redis序列化异常")
    public CacheErrorHandler errorHandler() {
        CacheErrorHandler ceh = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.warn("Redis序列化出现了查询异常");
                log.warn(key.toString());
            }
            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.warn("Redis序列化出现了插入异常");
                log.warn(key.toString());
            }
            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.warn("Redis序列化出现了Evict异常");
                log.warn(key.toString());
            }
            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.warn("Redis序列化出现了删除异常");
            }
        };
        return ceh;
    }

    @Bean
    @ApiOperation(value = "Redis序列化")
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        // 判断缓存格式化错误
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        PolymorphicTypeValidator ptv = new ObjectMapper().getPolymorphicTypeValidator();
        objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        // 判断乱码错误
        RedisCacheConfiguration rc = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .disableCachingNullValues();
        // 处理缓存时长
        Duration expireTime = Duration.ofDays(time);
        if(Objects.equals(unit,"hour")) {
            expireTime = Duration.ofHours(time);
        } else if(Objects.equals(unit,"minute")) {
            expireTime = Duration.ofMinutes(time);
        }
        return RedisCacheManager.builder(factory).cacheDefaults(rc.entryTtl(expireTime)).build();
    }
}
