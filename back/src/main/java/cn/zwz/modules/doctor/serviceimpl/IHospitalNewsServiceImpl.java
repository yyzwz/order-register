package cn.zwz.modules.doctor.serviceimpl;

import cn.zwz.modules.doctor.mapper.HospitalNewsMapper;
import cn.zwz.modules.doctor.entity.HospitalNews;
import cn.zwz.modules.doctor.service.IHospitalNewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 医院新闻接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IHospitalNewsServiceImpl extends ServiceImpl<HospitalNewsMapper, HospitalNews> implements IHospitalNewsService {

    @Autowired
    private HospitalNewsMapper hospitalNewsMapper;
}