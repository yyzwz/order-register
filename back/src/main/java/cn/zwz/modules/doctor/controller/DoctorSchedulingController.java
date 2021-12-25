package cn.zwz.modules.doctor.controller;

import cn.zwz.common.utils.PageUtil;
import cn.zwz.common.utils.ResultUtil;
import cn.zwz.common.vo.PageVo;
import cn.zwz.common.vo.Result;
import cn.zwz.modules.base.utils.ZwzNullUtils;
import cn.zwz.modules.doctor.entity.Doctor;
import cn.zwz.modules.doctor.entity.DoctorScheduling;
import cn.zwz.modules.doctor.service.IDoctorSchedulingService;
import cn.zwz.modules.doctor.service.IDoctorService;
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
@Api(description = "医生排班管理接口")
@RequestMapping("/zwz/doctorScheduling")
@Transactional
public class DoctorSchedulingController {

    @Autowired
    private IDoctorSchedulingService iDoctorSchedulingService;

    @Autowired
    private IDoctorService iDoctorService;

    @RequestMapping(value = "/addNumber", method = RequestMethod.POST)
    @ApiOperation(value = "医生放号")
    public Result<Object> addNumber(@RequestParam String doctorId,@RequestParam String date,@RequestParam int step,@RequestParam int number){
        Doctor doctor = iDoctorService.getById(doctorId);
        if(doctor == null) {
            return new ResultUtil<Object>().setErrorMsg("医生不存在");
        }
        if(number < 1) {
            return new ResultUtil<Object>().setErrorMsg("放号数必须大于0");
        }
        for(int i = 1 ; i <= number; i ++) {
            DoctorScheduling ds = new DoctorScheduling();
            ds.setDoctorId(doctor.getId());
            ds.setDoctorName(doctor.getDoctorName());
            ds.setNumber(i + "");
            ds.setStep(step + "");
            ds.setDate(date);
            ds.setOrderFlag(0);
            iDoctorSchedulingService.saveOrUpdate(ds);
        }
        return new ResultUtil<Object>().setSuccessMsg("放号成功");
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<DoctorScheduling> get(@PathVariable String id){

        DoctorScheduling doctorScheduling = iDoctorSchedulingService.getById(id);
        return new ResultUtil<DoctorScheduling>().setData(doctorScheduling);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<DoctorScheduling>> getAll(){

        List<DoctorScheduling> list = iDoctorSchedulingService.list();
        return new ResultUtil<List<DoctorScheduling>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<DoctorScheduling>> getByPage(@ModelAttribute DoctorScheduling scheduling,@ModelAttribute PageVo page){
        QueryWrapper<DoctorScheduling> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(scheduling.getDoctorId())) {
            qw.eq("doctor_id",scheduling.getDoctorId());
        }
        if(!ZwzNullUtils.isNull(scheduling.getDate())) {
            qw.eq("date",scheduling.getDate());
        }
        if(scheduling.getStep().equals("0")) {
            qw.eq("step","0");
        } else if(scheduling.getStep().equals("1")) {
            qw.eq("step","1");
        }
        IPage<DoctorScheduling> data = iDoctorSchedulingService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<DoctorScheduling>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<DoctorScheduling> saveOrUpdate(DoctorScheduling doctorScheduling){

        if(iDoctorSchedulingService.saveOrUpdate(doctorScheduling)){
            return new ResultUtil<DoctorScheduling>().setData(doctorScheduling);
        }
        return new ResultUtil<DoctorScheduling>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids){

        for(String id : ids){
            iDoctorSchedulingService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
