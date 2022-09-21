package cn.zwz.data.service;

import cn.zwz.data.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单 服务层接口
 * @author 郑为中
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<Permission> findByUserId(String userId);
}
