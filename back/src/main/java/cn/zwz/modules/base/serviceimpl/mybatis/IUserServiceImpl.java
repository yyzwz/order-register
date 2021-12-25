package cn.zwz.modules.base.serviceimpl.mybatis;

import cn.zwz.modules.base.dao.mapper.UserMapper;
import cn.zwz.modules.base.entity.User;
import cn.zwz.modules.base.service.mybatis.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 郑为中
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
}
