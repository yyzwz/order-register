package cn.zwz.test.serviceimpl;

import cn.zwz.test.mapper.TeacherMapper;
import cn.zwz.test.entity.Teacher;
import cn.zwz.test.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 教师 服务层接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class ITeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
}