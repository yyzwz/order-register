package cn.zwz.modules.doctor.serviceimpl;

import cn.zwz.modules.doctor.mapper.HospitalSubjectMapper;
import cn.zwz.modules.doctor.entity.HospitalSubject;
import cn.zwz.modules.doctor.service.IHospitalSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 科室接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IHospitalSubjectServiceImpl extends ServiceImpl<HospitalSubjectMapper, HospitalSubject> implements IHospitalSubjectService {

    @Autowired
    private HospitalSubjectMapper hospitalSubjectMapper;
}