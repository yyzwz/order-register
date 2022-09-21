package cn.zwz.doctor.serviceimpl;

import cn.zwz.doctor.mapper.HospitalNewsMapper;
import cn.zwz.doctor.entity.HospitalNews;
import cn.zwz.doctor.service.IHospitalNewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 医院新闻接口实现
 * @author 郑为中
 */
@Service
@Transactional
public class IHospitalNewsServiceImpl extends ServiceImpl<HospitalNewsMapper, HospitalNews> implements IHospitalNewsService {

}