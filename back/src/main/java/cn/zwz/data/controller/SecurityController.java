package cn.zwz.data.controller;

import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 未登录返回
 * @author 郑为中
 */
@RestController
@RequestMapping("/zwz/common")
@Api(tags = "未登录返回")
@Transactional
public class SecurityController {

    @RequestMapping(value = "/needLogin", method = RequestMethod.GET)
    @ApiOperation(value = "未登录返回的数据")
    public Result<Object> needLogin(){
        return ResultUtil.error(401, "登陆失效");
    }
}
