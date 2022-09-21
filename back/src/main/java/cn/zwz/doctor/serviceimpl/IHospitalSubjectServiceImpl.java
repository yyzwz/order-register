package cn.zwz.doctor.serviceimpl;

import cn.zwz.doctor.mapper.HospitalSubjectMapper;
import cn.zwz.doctor.entity.HospitalSubject;
import cn.zwz.doctor.service.IHospitalSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 科室接口实现
 * @author 郑为中
 */
@Service
@Transactional
public class IHospitalSubjectServiceImpl extends ServiceImpl<HospitalSubjectMapper, HospitalSubject> implements IHospitalSubjectService {

}