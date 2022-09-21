package cn.zwz.basics.security;

import cn.zwz.basics.security.jwt.JwtRoleFilter;
import cn.zwz.basics.utils.SecurityUtil;
import cn.zwz.basics.parameter.NoAuthenticationProperties;
import cn.zwz.basics.parameter.ZwzLoginProperties;
import cn.zwz.basics.security.jwt.AuthenticationFailHandler;
import cn.zwz.basics.security.jwt.AuthenticationSuccessHandler;
import cn.zwz.basics.security.jwt.ZwzAccessDeniedHandler;
import cn.zwz.basics.security.permission.MyFilterSecurityInterceptor;
import cn.zwz.basics.security.validate.ImageValidateFilter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity 配置
 * @author 郑为中
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ZwzLoginProperties zwzLoginProperties;

    @Autowired
    private NoAuthenticationProperties noAuthenticationProperties;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailHandler authenticationFailHandler;

    @Autowired
    private ZwzAccessDeniedHandler zwzAccessDeniedHandler;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    private ImageValidateFilter imageValidateFilter;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    @ApiOperation(value = "SpringSecurity配置")
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = httpSecurity.authorizeRequests();
        for(String authenticationUrl : noAuthenticationProperties.getAuthentication()){
            expressionInterceptUrlRegistry.antMatchers(authenticationUrl).permitAll();
        }
        expressionInterceptUrlRegistry.and().formLogin()
                // 默认提示登陆的接口
                .loginPage("/zwz/common/needLogin")
                // 默认登陆的接口
                .loginProcessingUrl("/zwz/login")
                .permitAll()
                // 登陆成功
                .successHandler(authenticationSuccessHandler)
                // 登陆失败
                .failureHandler(authenticationFailHandler)
                .and()
                // IFrame跨域
                .headers().frameOptions().disable()
                .and()
                .logout()
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                // 请求的身份认证
                .authenticated()
                .and()
                // 跨域配置
                .cors().and()
                // 关闭跨域拦截
                .csrf().disable()
                // 配置JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 系统菜单权限拦截
                .exceptionHandling().accessDeniedHandler(zwzAccessDeniedHandler)
                .and()
                // 验证码过滤
                .addFilterBefore(imageValidateFilter, UsernamePasswordAuthenticationFilter.class)
                // 备用过滤器
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // JWT认证过滤
                .addFilter(new JwtRoleFilter(authenticationManager(), zwzLoginProperties, stringRedisTemplate, securityUtil));
    }
}
