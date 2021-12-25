package cn.zwz.modules.base.service;

import cn.zwz.base.ZwzBaseService;
import cn.zwz.modules.base.entity.RolePermission;

import java.util.List;

/**
 * 角色权限接口
 * @author 郑为中
 */
public interface RolePermissionService extends ZwzBaseService<RolePermission,String> {

    /**
     * 通过permissionId获取
     * @param permissionId
     * @return
     */
    List<RolePermission> findByPermissionId(String permissionId);

    /**
     * 通过roleId获取
     * @param roleId
     * @return
     */
    List<RolePermission> findByRoleId(String roleId);

    /**
     * 通过roleId删除
     * @param roleId
     */
    void deleteByRoleId(String roleId);
}