package cn.zwz.data.serviceimpl;

import cn.zwz.data.dao.mapper.RoleMapper;
import cn.zwz.data.entity.Role;
import cn.zwz.data.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色 服务层实现
 * @author 郑为中
 */
@Service
public class IRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
