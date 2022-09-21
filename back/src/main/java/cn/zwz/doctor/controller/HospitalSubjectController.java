package cn.zwz.doctor.controller;

import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.doctor.entity.HospitalSubject;
import cn.zwz.doctor.service.IHospitalSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
@Api(tags = "科室管理")
@RequestMapping("/zwz/hospitalSubject")
@Transactional
public class HospitalSubjectController {

    @Autowired
    private IHospitalSubjectService iHospitalSubjectService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有科室")
    public Result<List<HospitalSubject>> getAll(){
        return new ResultUtil<List<HospitalSubject>>().setData(iHospitalSubjectService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询科室")
    public Result<IPage<HospitalSubject>> getByPage(@ModelAttribute HospitalSubject subject,@ModelAttribute PageVo page){
        QueryWrapper<HospitalSubject> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(subject.getSubName())) {
            qw.like("sub_name",subject.getSubName());
        }
        if(!ZwzNullUtils.isNull(subject.getSubCode())) {
            qw.like("sub_code",subject.getSubCode());
        }
        return new ResultUtil<IPage<HospitalSubject>>().setData(iHospitalSubjectService.page(PageUtil.initMpPage(page),qw));
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "新增修改科室")
    public Result<HospitalSubject> saveOrUpdate(HospitalSubject hospitalSubject){
        if(iHospitalSubjectService.saveOrUpdate(hospitalSubject)){
            return new ResultUtil<HospitalSubject>().setData(hospitalSubject);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除科室")
    public Result<Object> delAllByIds(@RequestParam String[] ids){
        for(String id : ids){
            iHospitalSubjectService.removeById(id);
        }
        return ResultUtil.success();
    }
}
