package cn.zwz.doctor.serviceimpl;

import cn.zwz.doctor.mapper.DoctorSchedulingMapper;
import cn.zwz.doctor.entity.DoctorScheduling;
import cn.zwz.doctor.service.IDoctorSchedulingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 医生排班接口实现
 * @author 郑为中
 */
@Service
@Transactional
public class IDoctorSchedulingServiceImpl extends ServiceImpl<DoctorSchedulingMapper, DoctorScheduling> implements IDoctorSchedulingService {

}