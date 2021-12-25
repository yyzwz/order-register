package cn.zwz.modules.doctor.controller;

import cn.zwz.common.utils.PageUtil;
import cn.zwz.common.utils.ResultUtil;
import cn.zwz.common.vo.PageVo;
import cn.zwz.common.vo.Result;
import cn.zwz.modules.base.utils.ZwzNullUtils;
import cn.zwz.modules.doctor.entity.HospitalSubject;
import cn.zwz.modules.doctor.service.IHospitalSubjectService;
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
@Api(description = "科室管理接口")
@RequestMapping("/zwz/hospitalSubject")
@Transactional
public class HospitalSubjectController {

    @Autowired
    private IHospitalSubjectService iHospitalSubjectService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<HospitalSubject> get(@PathVariable String id){

        HospitalSubject hospitalSubject = iHospitalSubjectService.getById(id);
        return new ResultUtil<HospitalSubject>().setData(hospitalSubject);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<HospitalSubject>> getAll(){

        List<HospitalSubject> list = iHospitalSubjectService.list();
        return new ResultUtil<List<HospitalSubject>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<HospitalSubject>> getByPage(@ModelAttribute HospitalSubject subject,@ModelAttribute PageVo page){
        QueryWrapper<HospitalSubject> qw = new QueryWrapper<>();
        if(subject.getSubName() != null && !ZwzNullUtils.isNull(subject.getSubName())) {
            qw.like("sub_name",subject.getSubName());
        }
        if(subject.getSubCode() != null && !ZwzNullUtils.isNull(subject.getSubCode())) {
            qw.like("sub_code",subject.getSubCode());
        }
        IPage<HospitalSubject> data = iHospitalSubjectService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<HospitalSubject>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<HospitalSubject> saveOrUpdate(HospitalSubject hospitalSubject){

        if(iHospitalSubjectService.saveOrUpdate(hospitalSubject)){
            return new ResultUtil<HospitalSubject>().setData(hospitalSubject);
        }
        return new ResultUtil<HospitalSubject>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids){

        for(String id : ids){
            iHospitalSubjectService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
