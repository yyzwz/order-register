package cn.zwz.basics.baseClass;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * SpringBoot启动配置类
 * @author 郑为中
 */
@Configuration
@Slf4j
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class BeansConfiguration {

    @Bean
    @ApiOperation(value = "初始化PathMatcher")
    public PathMatcher pathMatcher(){
        log.info("初始化PathMatcher成功");
        return new AntPathMatcher();
    }

    @Primary
    @Bean
    @ApiOperation(value = "初始化线程池")
    public TaskExecutor primaryTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        log.info("初始化线程池成功");
        return executor;
    }
}
