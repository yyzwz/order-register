package cn.zwz.modules.doctor.controller;

import cn.zwz.common.utils.PageUtil;
import cn.zwz.common.utils.ResultUtil;
import cn.zwz.common.vo.PageVo;
import cn.zwz.common.vo.Result;
import cn.zwz.modules.doctor.entity.HospitalNews;
import cn.zwz.modules.doctor.service.IHospitalNewsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 郑为中
 */
@RestController
@Api(tags = "医院新闻管理")
@RequestMapping("/zwz/hospitalNews")
@Transactional
public class HospitalNewsController {

    @Autowired
    private IHospitalNewsService iHospitalNewsService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询单个医院新闻")
    public Result<HospitalNews> get(@PathVariable String id){
        return new ResultUtil<HospitalNews>().setData(iHospitalNewsService.getById(id));
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有医院新闻")
    public Result<List<HospitalNews>> getAll(){
        return new ResultUtil<List<HospitalNews>>().setData(iHospitalNewsService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询医院新闻")
    public Result<IPage<HospitalNews>> getByPage(PageVo page){
        return new ResultUtil<IPage<HospitalNews>>().setData(iHospitalNewsService.page(PageUtil.initMpPage(page)));
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改医院新闻")
    public Result<HospitalNews> saveOrUpdate(HospitalNews hospitalNews){
        if(iHospitalNewsService.saveOrUpdate(hospitalNews)){
            return new ResultUtil<HospitalNews>().setData(hospitalNews);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除医院新闻")
    public Result<Object> delAllByIds(@RequestParam String[] ids){
        for(String id : ids){
            iHospitalNewsService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/set_top_by_id", method = RequestMethod.POST)
    @ApiOperation(value = "置顶医院新闻")
    public Result<Boolean> setTopById(@RequestParam String id){
        HospitalNews appDynamicNew=iHospitalNewsService.getById(id);
        if(appDynamicNew==null) {
            return ResultUtil.error("该动态信息不存在");
        }
        if(appDynamicNew.getIsTop().equals("yes")) {
            appDynamicNew.setIsTop("no");
        }
        else {
            appDynamicNew.setIsTop("yes");
        }
        if(!iHospitalNewsService.saveOrUpdate(appDynamicNew)) {
            return ResultUtil.error("设置失败");
        }
        return ResultUtil.success("设置成功");
    }

    @RequestMapping(value = "/set_public_by_id", method = RequestMethod.POST)
    @ApiOperation(value = "公开医院新闻")
    public Result<Boolean> setPublicById(@RequestParam String id){
        HospitalNews appDynamicNew=iHospitalNewsService.getById(id);
        if(appDynamicNew==null) {
            return ResultUtil.error("该动态信息不存在");
        }
        if(appDynamicNew.getIsPublic().equals("yes")) {
            appDynamicNew.setIsPublic("no");
        }
        else {
            appDynamicNew.setIsPublic("yes");
        }
        if(!iHospitalNewsService.saveOrUpdate(appDynamicNew)) {
            return ResultUtil.error("设置失败");
        }
        return ResultUtil.success("设置成功");
    }
}
