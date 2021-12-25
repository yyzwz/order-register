package cn.zwz.modules.doctor.serviceimpl;

import cn.zwz.modules.doctor.mapper.DoctorSchedulingMapper;
import cn.zwz.modules.doctor.entity.DoctorScheduling;
import cn.zwz.modules.doctor.service.IDoctorSchedulingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 医生排班接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IDoctorSchedulingServiceImpl extends ServiceImpl<DoctorSchedulingMapper, DoctorScheduling> implements IDoctorSchedulingService {

    @Autowired
    private DoctorSchedulingMapper doctorSchedulingMapper;
}