package cn.zwz.modules.doctor.serviceimpl;

import cn.zwz.modules.doctor.mapper.HospitalOrderMapper;
import cn.zwz.modules.doctor.entity.HospitalOrder;
import cn.zwz.modules.doctor.service.IHospitalOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 挂号接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IHospitalOrderServiceImpl extends ServiceImpl<HospitalOrderMapper, HospitalOrder> implements IHospitalOrderService {

    @Autowired
    private HospitalOrderMapper hospitalOrderMapper;
}