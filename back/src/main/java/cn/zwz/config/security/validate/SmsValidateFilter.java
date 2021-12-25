package cn.zwz.config.security.validate;

import cn.zwz.common.constant.CommonConstant;
import cn.zwz.common.utils.ResponseUtil;
import cn.zwz.config.properties.CaptchaProperties;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图形验证码过滤器
 * @author 郑为中
 */
@Slf4j
@Configuration
public class SmsValidateFilter extends OncePerRequestFilter {

    @Autowired
    private CaptchaProperties captchaProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PathMatcher pathMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 判断URL是否需要验证
        Boolean flag = false;
        String requestUrl = request.getRequestURI();
        for(String url : captchaProperties.getSms()){
            if(pathMatcher.match(url, requestUrl)){
                flag = true;
                break;
            }
        }
        if(flag){
            String mobile = request.getParameter("mobile");
            String code = request.getParameter("code");
            if(StrUtil.isBlank(mobile)||StrUtil.isBlank(code)){
                ResponseUtil.out(response, ResponseUtil.resultMap(false,500,"请传入短信验证码所需参数mobile或code"));
                return;
            }
            String redisCode = redisTemplate.opsForValue().get(CommonConstant.PRE_SMS + mobile);
            if(StrUtil.isBlank(redisCode)){
                ResponseUtil.out(response, ResponseUtil.resultMap(false,500,"验证码已过期或不存在，请重新获取"));
                return;
            }

            if(!redisCode.toLowerCase().equals(code.toLowerCase())) {
                log.info("验证码错误：code:"+ code +"，redisCode:"+redisCode);
                ResponseUtil.out(response, ResponseUtil.resultMap(false,500,"短信验证码输入错误"));
                return;
            }
            // 已验证清除key
            redisTemplate.delete(CommonConstant.PRE_SMS + mobile);
            // 验证成功 放行
            chain.doFilter(request, response);
            return;
        }
        // 无需验证 放行
        chain.doFilter(request, response);
    }
}
