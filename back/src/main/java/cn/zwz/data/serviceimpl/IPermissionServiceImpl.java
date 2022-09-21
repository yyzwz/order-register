package cn.zwz.data.serviceimpl;

import cn.zwz.data.dao.mapper.PermissionMapper;
import cn.zwz.data.entity.Permission;
import cn.zwz.data.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单 服务层实现
 * @author 郑为中
 */
@Service
public class IPermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findByUserId(String userId) {
        return permissionMapper.findByUserId(userId);
    }
}
