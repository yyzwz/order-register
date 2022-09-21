package cn.zwz.data.controller;

import cn.zwz.basics.log.LogType;
import cn.zwz.basics.log.SystemLog;
import cn.zwz.basics.redis.RedisTemplateHelper;
import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.data.entity.*;
import cn.zwz.data.service.IRolePermissionService;
import cn.zwz.data.service.IRoleService;
import cn.zwz.data.service.IUserRoleService;
import cn.zwz.data.utils.ZwzNullUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 角色
 * @author 郑为中
 */
@RestController
@Api(tags = "角色管理接口")
@RequestMapping("/zwz/role")
@Transactional
public class RoleController {

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IUserRoleService iUserRoleService;

    @Autowired
    private IRolePermissionService iRolePermissionService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisTemplateHelper redisTemplateHelper;

    @SystemLog(about = "查询全部角色", type = LogType.DATA_CENTER,doType = "ROLE-01")
    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部角色")
    public Result<Object> getAllList(){
        return ResultUtil.data(iRoleService.list());
    }

    @SystemLog(about = "查询角色", type = LogType.DATA_CENTER,doType = "ROLE-02")
    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询角色")
    public Result<IPage<Role>> getRoleByPage(@ModelAttribute Role role,@ModelAttribute PageVo page) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(role.getName())) {
            qw.like("name",role.getName());
        }
        if(!ZwzNullUtils.isNull(role.getDescription())) {
            qw.like("description",role.getDescription());
        }
        IPage<Role> roleList = iRoleService.page(PageUtil.initMpPage(page),qw);
        for(Role r : roleList.getRecords()){
            QueryWrapper<RolePermission> rpQw = new QueryWrapper<>();
            rpQw.eq("role_id",r.getId());
            r.setPermissions(iRolePermissionService.list(rpQw));
        }
        return new ResultUtil<IPage<Role>>().setData(roleList);
    }

    @SystemLog(about = "配置默认角色", type = LogType.DATA_CENTER,doType = "ROLE-03")
    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    @ApiOperation(value = "配置默认角色")
    public Result<Object> setDefault(@RequestParam String id,@RequestParam Boolean isDefault){
        Role role = iRoleService.getById(id);
        if(role != null) {
            if(!Objects.equals(role.getDefaultRole(),isDefault)) {
                role.setDefaultRole(isDefault);
                iRoleService.saveOrUpdate(role);
            }
            return ResultUtil.success();
        }
        return ResultUtil.error("不存在");
    }

    @SystemLog(about = "修改菜单权限", type = LogType.DATA_CENTER,doType = "ROLE-04")
    @RequestMapping(value = "/editRolePerm", method = RequestMethod.POST)
    @ApiOperation(value = "修改菜单权限")
    public Result<Object> editRolePerm(@RequestParam String roleId,@RequestParam(required = false) String[] permIds){
        Role role = iRoleService.getById(roleId);
        if(role == null) {
            return ResultUtil.error("角色已被删除");
        }
        if(permIds == null) {
            permIds = new String[0];
        }
        QueryWrapper<RolePermission> oldQw = new QueryWrapper<>();
        oldQw.eq("role_id",role.getId());
        List<RolePermission> oldPermissionList = iRolePermissionService.list(oldQw);
        // 判断新增 = oldPermissionList没有 permIds有
        for (String permId : permIds) {
            boolean flag = true;
            for (RolePermission rp : oldPermissionList) {
                if(Objects.equals(permId,rp.getPermissionId())) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                RolePermission rp = new RolePermission();
                rp.setRoleId(role.getId());
                rp.setPermissionId(permId);
                iRolePermissionService.saveOrUpdate(rp);
            }
        }
        // 判断删除 = oldPermissionList有 permIds没有
        for (RolePermission rp : oldPermissionList) {
            boolean flag = true;
            for (String permId : permIds) {
                if(Objects.equals(permId,rp.getPermissionId())) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                iRolePermissionService.removeById(rp.getId());
            }
        }
        Set<String> keysUser = redisTemplateHelper.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplateHelper.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        Set<String> keysUserMenu = redisTemplateHelper.keys("permission::userMenuList:*");
        redisTemplate.delete(keysUserMenu);
        return ResultUtil.data();
    }

    @SystemLog(about = "新增角色", type = LogType.DATA_CENTER,doType = "ROLE-05")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增角色")
    public Result<Role> save(Role role){
        iRoleService.saveOrUpdate(role);
        return new ResultUtil<Role>().setData(role);
    }

    @SystemLog(about = "编辑角色", type = LogType.DATA_CENTER,doType = "ROLE-06")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色")
    public Result<Role> edit(Role role){
        iRoleService.saveOrUpdate(role);
        Set<String> keysUser = redisTemplateHelper.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplateHelper.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        return new ResultUtil<Role>().setData(role);
    }

    @SystemLog(about = "删除角色", type = LogType.DATA_CENTER,doType = "ROLE-07")
    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除角色")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids) {
            QueryWrapper<UserRole> urQw = new QueryWrapper<>();
            urQw.eq("role_id", id);
            long urCount = iUserRoleService.count(urQw);
            if(urCount > 0L){
                return ResultUtil.error("不能删除正在使用的角色");
            }
        }
        for(String id:ids){
            iRoleService.removeById(id);
            QueryWrapper<RolePermission> rpQw = new QueryWrapper<>();
            rpQw.eq("role_id",id);
            iRolePermissionService.remove(rpQw);
        }
        return ResultUtil.success();
    }
}
