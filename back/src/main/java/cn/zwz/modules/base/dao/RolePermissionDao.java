package cn.zwz.modules.base.dao;

import cn.zwz.base.ZwzBaseDao;
import cn.zwz.modules.base.entity.RolePermission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 角色权限数据处理层
 * @author 郑为中
 */
public interface RolePermissionDao extends ZwzBaseDao<RolePermission,String> {

    /**
     * 通过permissionId获取
     * @param permissionId
     * @return
     */
    List<RolePermission> findByPermissionId(String permissionId);

    /**
     * 通过roleId获取
     * @param roleId
     */
    List<RolePermission> findByRoleId(String roleId);

    /**
     * 通过roleId删除
     * @param roleId
     */
    @Modifying
    @Query("delete from RolePermission r where r.roleId = ?1")
    void deleteByRoleId(String roleId);
}