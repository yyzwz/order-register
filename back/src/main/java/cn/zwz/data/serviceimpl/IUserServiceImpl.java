package cn.zwz.data.serviceimpl;

import cn.zwz.data.dao.mapper.UserMapper;
import cn.zwz.data.entity.User;
import cn.zwz.data.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户 服务层实现
 * @author 郑为中
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
