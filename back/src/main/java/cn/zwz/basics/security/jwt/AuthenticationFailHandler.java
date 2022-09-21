package cn.zwz.basics.security.jwt;

import cn.zwz.basics.utils.ResponseUtil;
import cn.zwz.basics.exception.ZwzAuthException;
import cn.zwz.basics.parameter.ZwzLoginProperties;
import cn.zwz.data.utils.ZwzNullUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author 郑为中
 */
@Slf4j
@Component
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ZwzLoginProperties tokenProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String LOGIN_FAIL_TIMES_PRE = "LOGIN_FAIL_TIMES_PRE:";

    private static final String REQUEST_PARAMETER_USERNAME = "username:";

    private static final boolean RESPONSE_FAIL_FLAG = false;

    private static final int RESPONSE_FAIL_CODE = 500;

    @ApiOperation(value = "查询登陆失败的次数")
    public boolean recordLoginTime(String username) {
        String loginFailTimeStr = stringRedisTemplate.opsForValue().get(LOGIN_FAIL_TIMES_PRE + username);
        int loginFailTime = 0;
        // 已错误次数
        if(!ZwzNullUtils.isNull(loginFailTimeStr)){
            loginFailTime = Integer.parseInt(loginFailTimeStr) + 1;
        }
        stringRedisTemplate.opsForValue().set(LOGIN_FAIL_TIMES_PRE + username, loginFailTime + "", tokenProperties.getLoginFailMaxThenLockTimes(), TimeUnit.MINUTES);
        if(loginFailTime >= tokenProperties.getMaxLoginFailTimes()){
            stringRedisTemplate.opsForValue().set("userLoginDisableFlag:"+username, "fail", tokenProperties.getLoginFailMaxThenLockTimes(), TimeUnit.MINUTES);
            return false;
        }
        return true;
    }

    @Override
    @ApiOperation(value = "登陆失败回调")
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
            recordLoginTime(request.getParameter(REQUEST_PARAMETER_USERNAME));
            String failTimesStr = stringRedisTemplate.opsForValue().get(LOGIN_FAIL_TIMES_PRE + request.getParameter(REQUEST_PARAMETER_USERNAME));
            //已错误的次数
            int userFailTimes = 0;
            if(!ZwzNullUtils.isNull(failTimesStr)){
                userFailTimes = Integer.parseInt(failTimesStr);
            }
            int restLoginTime = tokenProperties.getMaxLoginFailTimes() - userFailTimes;
            if(restLoginTime < 5 && restLoginTime > 0){
                ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,"账号密码不正确，还能尝试登陆" + restLoginTime + "次"));
            } else if(restLoginTime < 1) {
                ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,"重试超限，请您" + tokenProperties.getLoginFailMaxThenLockTimes() + "分后再登陆"));
            } else {
                ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,"账号密码不正确"));
            }
        } else if (exception instanceof ZwzAuthException){
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,((ZwzAuthException) exception).getMsg()));
        } else if (exception instanceof DisabledException) {
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,"账户处于禁用状态，无法登陆"));
        } else {
            ResponseUtil.out(response, ResponseUtil.resultMap(RESPONSE_FAIL_FLAG,RESPONSE_FAIL_CODE,"系统当前不能登陆，请稍后再试"));
        }
    }
}
