package cn.zwz.modules.doctor.serviceimpl;

import cn.zwz.modules.doctor.mapper.DoctorMapper;
import cn.zwz.modules.doctor.entity.Doctor;
import cn.zwz.modules.doctor.service.IDoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 医生接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IDoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements IDoctorService {

    @Autowired
    private DoctorMapper doctorMapper;
}