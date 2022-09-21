package cn.zwz.basics.redis;

import cn.zwz.basics.parameter.NoAuthenticationProperties;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * @author 郑为中
 */
@Slf4j
@Configuration
public class InterceptConfiguration implements WebMvcConfigurer {

    @Autowired
    private NoAuthenticationProperties noAuthenticationProperties;

    @Autowired
    private LimitRaterInterceptor limitRaterInterceptor;

    private static final String PATH_PATTERN_STR = "/**";

    @Override
    @ApiOperation(value = "重写鉴权拦截逻辑")
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(limitRaterInterceptor);
        registration.addPathPatterns(PATH_PATTERN_STR);
        registration.excludePathPatterns(noAuthenticationProperties.getLimiting());
        log.info("拦截器加载成功");
    }
}
