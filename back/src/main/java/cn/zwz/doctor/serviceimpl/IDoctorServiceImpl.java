package cn.zwz.doctor.serviceimpl;

import cn.zwz.doctor.mapper.DoctorMapper;
import cn.zwz.doctor.entity.Doctor;
import cn.zwz.doctor.service.IDoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 医生接口实现
 * @author 郑为中
 */
@Service
@Transactional
public class IDoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements IDoctorService {

}