package cn.zwz.data.controller;

import cn.zwz.basics.utils.*;
import cn.zwz.basics.baseVo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 郑为中
 */
@Api(tags = "验证码接口")
@RequestMapping("/zwz/common/captcha")
@RestController
@Transactional
public class CaptchaController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/draw/{captchaId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据验证码ID获取图片")
    public void draw(@PathVariable("captchaId") String captchaId,HttpServletResponse response) throws IOException {
        String codeStr = redisTemplate.opsForValue().get(captchaId);
        CreateVerifyCode createVerifyCode = new CreateVerifyCode(116,36,4,10, codeStr);
        response.setContentType("image/png");
        createVerifyCode.write(response.getOutputStream());
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @ApiOperation(value = "初始化验证码")
    public Result<Object> init() {
        String codeId = UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set(codeId, new CreateVerifyCode().randomStr(4),2L, TimeUnit.MINUTES);
        return ResultUtil.data(codeId);
    }
}
