package cn.zwz.basics.security.controller;

import cn.zwz.basics.baseVo.Result;
import cn.zwz.basics.log.LogType;
import cn.zwz.basics.log.SystemLog;
import cn.zwz.basics.redis.RedisTemplateHelper;
import cn.zwz.basics.security.SecurityUserDetails;
import cn.zwz.basics.security.utils.WeiChatUtils;
import cn.zwz.basics.security.utils.ZwzWxNoticeUtils;
import cn.zwz.basics.utils.CommonUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.utils.SecurityUtil;
import cn.zwz.data.entity.User;
import cn.zwz.data.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 企业微信验证码
 * @author 郑为中
 */
@RestController
@Api(tags = "企微验证码")
@RequestMapping("/zwz/qwVerificationCode")
@Transactional
public class QwVerificationCodeController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @ApiOperation(value = "发送企微验证码")
    @RequestMapping(value = "/sendVerificationCode", method = RequestMethod.GET)
    public Result<Object> sendVerificationCode(@RequestParam String jobNumber) {
        if(!Objects.equals("zwz",jobNumber)) {
            return ResultUtil.error("请联系管理员配置您的工号");
        }
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("status", 0);
        userQw.eq("username",jobNumber);
        Long userCount = iUserService.count(userQw);
        if(userCount < 1L) {
            return new ResultUtil<Object>().setErrorMsg("无权限登入");
        }
        String verificationCode = CommonUtil.getRandomTwoNum();
        /**
         * 这里需要实现判断发给谁的业务逻辑
         */
        ZwzWxNoticeUtils.sendTuWenMessage("zwz", "OA登陆验证","验证码 " + verificationCode + "，1分钟后失效","https://gitee.com/yyzwz","https://bkimg.cdn.bcebos.com/pic/37d12f2eb9389b503a80d4b38b35e5dde6116ed7", WeiChatUtils.getToken());
        redisTemplate.set("qwsms:" + jobNumber,verificationCode,60, TimeUnit.SECONDS);
        return ResultUtil.success();
    }

    @SystemLog(about = "企微验证码登入", type = LogType.LOGIN)
    @ApiOperation(value = "企微验证码登入")
    @RequestMapping(value = "/verificationCodeLogin", method = RequestMethod.GET)
    public Result<Object> verificationCodeLogin(@RequestParam String jobNumber,@RequestParam String code){
        String codeAns = redisTemplate.get("qwsms:" + jobNumber);
        if(codeAns == null) {
            return ResultUtil.error("验证码已过期");
        }
        if(codeAns.equals(code)) {
            QueryWrapper<User> userQw = new QueryWrapper<>();
            userQw.eq("username",jobNumber);
            List<User> users = iUserService.list(userQw);
            if(users.size() == 0) {
                return ResultUtil.error(jobNumber + "账户不存在");
            }
            String accessToken = securityUtil.getToken(jobNumber, false);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(new SecurityUserDetails(users.get(0)), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResultUtil.data(accessToken);
        }
        return ResultUtil.error("验证码错误");
    }
}
