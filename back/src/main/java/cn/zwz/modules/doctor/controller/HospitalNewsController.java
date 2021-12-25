package cn.zwz.modules.doctor.controller;

import cn.zwz.common.utils.PageUtil;
import cn.zwz.common.utils.ResultUtil;
import cn.zwz.common.vo.PageVo;
import cn.zwz.common.vo.Result;
import cn.zwz.modules.doctor.entity.HospitalNews;
import cn.zwz.modules.doctor.service.IHospitalNewsService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 郑为中
 */
@Slf4j
@RestController
@Api(description = "医院新闻管理接口")
@RequestMapping("/zwz/hospitalNews")
@Transactional
public class HospitalNewsController {

    @Autowired
    private IHospitalNewsService iHospitalNewsService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<HospitalNews> get(@PathVariable String id){

        HospitalNews hospitalNews = iHospitalNewsService.getById(id);
        return new ResultUtil<HospitalNews>().setData(hospitalNews);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<HospitalNews>> getAll(){

        List<HospitalNews> list = iHospitalNewsService.list();
        return new ResultUtil<List<HospitalNews>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<HospitalNews>> getByPage(PageVo page){

        IPage<HospitalNews> data = iHospitalNewsService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<HospitalNews>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<HospitalNews> saveOrUpdate(HospitalNews hospitalNews){

        if(iHospitalNewsService.saveOrUpdate(hospitalNews)){
            return new ResultUtil<HospitalNews>().setData(hospitalNews);
        }
        return new ResultUtil<HospitalNews>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids){

        for(String id : ids){
            iHospitalNewsService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/set_top_by_id", method = RequestMethod.POST)
    @ApiOperation(value = "设置置顶")
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
    @ApiOperation(value = "设置公开")
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
