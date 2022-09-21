package cn.zwz.data.controller;

import cn.zwz.basics.utils.IpInfoUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * IP定位
 * @author 郑为中
 */
@RestController
@Api(tags = "IP接口")
@RequestMapping("/zwz/common/ip")
@Transactional
public class IpInfoController {

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "IP信息")
    public Result<Object> upload(HttpServletRequest request) {
        return ResultUtil.data(ipInfoUtil.getIpCity(request));
    }
}