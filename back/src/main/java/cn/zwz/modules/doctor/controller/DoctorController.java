package cn.zwz.modules.doctor.controller;

import cn.hutool.core.date.DateUtil;
import cn.zwz.common.utils.PageUtil;
import cn.zwz.common.utils.ResultUtil;
import cn.zwz.common.vo.PageVo;
import cn.zwz.common.vo.Result;
import cn.zwz.modules.base.utils.ZwzNullUtils;
import cn.zwz.modules.doctor.entity.Doctor;
import cn.zwz.modules.doctor.service.IDoctorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author 郑为中
 */
@RestController
@Api(tags = "医生管理")
@RequestMapping("/zwz/doctor")
@Transactional
public class DoctorController {

    @Autowired
    private IDoctorService iDoctorService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询单个医生")
    public Result<Doctor> get(@PathVariable String id){
        return new ResultUtil<Doctor>().setData(iDoctorService.getById(id));
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有医生")
    public Result<List<Doctor>> getAll(){
        return new ResultUtil<List<Doctor>>().setData(iDoctorService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询医生")
    public Result<IPage<Doctor>> getByPage(@ModelAttribute Doctor doctor,@ModelAttribute PageVo page){
        QueryWrapper<Doctor> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(doctor.getDoctorName())) {
            qw.like("doctor_name",doctor.getDoctorName());
        }
        if(!ZwzNullUtils.isNull(doctor.getPostLevel())) {
            qw.eq("post_level",doctor.getPostLevel());
        }
        if(!ZwzNullUtils.isNull(doctor.getSubjectId())) {
            qw.eq("subject_id",doctor.getSubjectId());
        }
        if(!ZwzNullUtils.isNull(doctor.getNoeDate()) && Objects.equals(doctor.getNoeDate(),"是")) {
            qw.inSql("id","SELECT id FROM t_doctor WHERE id IN(SELECT DISTINCT doctor_id FROM t_doctor_scheduling WHERE DATE = '" + DateUtil.today() + "')");
        }
        return new ResultUtil<IPage<Doctor>>().setData(iDoctorService.page(PageUtil.initMpPage(page),qw));
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改医生")
    public Result<Doctor> saveOrUpdate(Doctor doctor){
        if(iDoctorService.saveOrUpdate(doctor)){
            return new ResultUtil<Doctor>().setData(doctor);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除医生")
    public Result<Object> delAllByIds(@RequestParam String[] ids){
        for(String id : ids){
            iDoctorService.removeById(id);
        }
        return ResultUtil.success();
    }
}
