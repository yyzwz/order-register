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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 郑为中
 */
@RestController
@Api(tags = "医生排班管理")
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
            return ResultUtil.error("医生不存在");
        }
        if(number < 1) {
            return ResultUtil.error("放号数必须大于0");
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
        return ResultUtil.success("放号成功");
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询单个医生排班")
    public Result<DoctorScheduling> get(@PathVariable String id){
        return new ResultUtil<DoctorScheduling>().setData(iDoctorSchedulingService.getById(id));
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有医生排班")
    public Result<List<DoctorScheduling>> getAll(){
        return new ResultUtil<List<DoctorScheduling>>().setData(iDoctorSchedulingService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询医生排班")
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
        return new ResultUtil<IPage<DoctorScheduling>>().setData(iDoctorSchedulingService.page(PageUtil.initMpPage(page),qw));
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改医生排班")
    public Result<DoctorScheduling> saveOrUpdate(DoctorScheduling doctorScheduling){
        if(iDoctorSchedulingService.saveOrUpdate(doctorScheduling)){
            return new ResultUtil<DoctorScheduling>().setData(doctorScheduling);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除医生排班")
    public Result<Object> delAllByIds(@RequestParam String[] ids){
        for(String id : ids){
            iDoctorSchedulingService.removeById(id);
        }
        return ResultUtil.success();
    }
}