package cn.zwz.modules.doctor.controller;

import cn.zwz.common.utils.PageUtil;
import cn.zwz.common.utils.ResultUtil;
import cn.zwz.common.vo.PageVo;
import cn.zwz.common.vo.Result;
import cn.zwz.modules.base.utils.ZwzNullUtils;
import cn.zwz.modules.doctor.entity.Doctor;
import cn.zwz.modules.doctor.service.IDoctorService;
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
import java.util.Objects;

/**
 * @author 郑为中
 */
@Slf4j
@RestController
@Api(description = "医生管理接口")
@RequestMapping("/zwz/doctor")
@Transactional
public class DoctorController {

    @Autowired
    private IDoctorService iDoctorService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<Doctor> get(@PathVariable String id){

        Doctor doctor = iDoctorService.getById(id);
        return new ResultUtil<Doctor>().setData(doctor);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<Doctor>> getAll(){

        List<Doctor> list = iDoctorService.list();
        return new ResultUtil<List<Doctor>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<Doctor>> getByPage(@ModelAttribute Doctor doctor,@ModelAttribute PageVo page){
        QueryWrapper<Doctor> qw = new QueryWrapper<>();
        if(doctor.getDoctorName() != null && !ZwzNullUtils.isNull(doctor.getDoctorName())) {
            qw.like("doctor_name",doctor.getDoctorName());
        }
        if(doctor.getPostLevel() != null && !ZwzNullUtils.isNull(doctor.getPostLevel())) {
            qw.eq("post_level",doctor.getPostLevel());
        }
        if(doctor.getSubjectId() != null && !ZwzNullUtils.isNull(doctor.getSubjectId())) {
            qw.eq("subject_id",doctor.getSubjectId());
        }
        IPage<Doctor> data = iDoctorService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<Doctor>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<Doctor> saveOrUpdate(Doctor doctor){

        if(iDoctorService.saveOrUpdate(doctor)){
            return new ResultUtil<Doctor>().setData(doctor);
        }
        return new ResultUtil<Doctor>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids){

        for(String id : ids){
            iDoctorService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
