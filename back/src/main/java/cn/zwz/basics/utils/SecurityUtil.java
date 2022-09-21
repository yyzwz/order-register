package cn.zwz.basics.utils;

import cn.zwz.basics.exception.ZwzException;
import cn.zwz.basics.baseVo.TokenUser;
import cn.zwz.basics.parameter.ZwzLoginProperties;
import cn.zwz.data.entity.*;
import cn.zwz.data.service.IPermissionService;
import cn.zwz.data.service.IRoleService;
import cn.zwz.data.service.IUserService;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.data.vo.PermissionDTO;
import cn.zwz.data.vo.RoleDTO;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 鉴权工具类
 * @author 郑为中
 */
@Component
public class SecurityUtil {

    @Autowired
    private ZwzLoginProperties tokenProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IPermissionService iPermissionService;

    private static final String TOKEN_REPLACE_FRONT_STR = "-";

    private static final String TOKEN_REPLACE_BACK_STR = "";

    private User selectByUserName(String title) {
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("username",title);
        User user = iUserService.getOne(userQw);
        if(user == null) {
            return null;
        }
        /**
         * 填充角色
         */
        QueryWrapper<Role> roleQw = new QueryWrapper<>();
        roleQw.inSql("id","SELECT role_id FROM a_user_role WHERE del_flag = 0 AND user_id = '" + user.getId() + "'");
        List<Role> roleList = iRoleService.list(roleQw);
        List<RoleDTO> roles = new ArrayList<>();
        for (Role role : roleList) {
            roles.add(new RoleDTO(role.getName(),role.getId(),role.getDescription()));
        }
        user.setRoles(roles);
        /**
         * 填充菜单
         */
        QueryWrapper<Permission> permissionQw = new QueryWrapper<>();
        permissionQw.inSql("id","SELECT role_id FROM a_role_permission WHERE del_flag = 0 AND permission_id = '" + user.getId() + "'");
        List<Permission> permissionList = iPermissionService.list(permissionQw);
        List<PermissionDTO> permissions = new ArrayList<>();
        for (Permission permission : permissionList) {
            if(!Objects.equals(1,permission.getType())) {
                permissions.add(new PermissionDTO(permission.getPath(),permission.getTitle()));
            }
        }
        user.setPermissions(permissions);
        return user;
    }

    @ApiOperation(value = "获取新的用户Token")
    public String getToken(String username, Boolean saveLogin){
        if(ZwzNullUtils.isNull(username)){
            throw new ZwzException("username不能为空");
        }
        boolean saved = false;
        if(saveLogin == null || saveLogin){
            saved = true;
        }
        User selectUser = selectByUserName(username);
        // 菜单和角色的数组
        List<String> permissionTitleList = new ArrayList<>();
        if(tokenProperties.getSaveRoleFlag()){
            for(PermissionDTO p : selectUser.getPermissions()){
                if(!ZwzNullUtils.isNull(p.getTitle()) && !ZwzNullUtils.isNull(p.getPath())) {
                    permissionTitleList.add(p.getTitle());
                }
            }
            for(RoleDTO r : selectUser.getRoles()){
                permissionTitleList.add(r.getName());
            }
        }
        String ansUserToken = UUID.randomUUID().toString().replace(TOKEN_REPLACE_FRONT_STR, TOKEN_REPLACE_BACK_STR);
        TokenUser tokenUser = new TokenUser(selectUser.getUsername(), permissionTitleList, saved);
        // 单点登录删除旧Token
        if(tokenProperties.getSsoFlag()) {
            String oldToken = redisTemplate.opsForValue().get(ZwzLoginProperties.USER_TOKEN_PRE + selectUser.getUsername());
            if (StrUtil.isNotBlank(oldToken)) {
                redisTemplate.delete(ZwzLoginProperties.HTTP_TOKEN_PRE + oldToken);
            }
        }
        // 保存至Redis备查
        if(saved){
            redisTemplate.opsForValue().set(ZwzLoginProperties.USER_TOKEN_PRE + selectUser.getUsername(), ansUserToken, tokenProperties.getUserSaveLoginTokenDays(), TimeUnit.DAYS);
            redisTemplate.opsForValue().set(ZwzLoginProperties.HTTP_TOKEN_PRE + ansUserToken, new Gson().toJson(tokenUser), tokenProperties.getUserSaveLoginTokenDays(), TimeUnit.DAYS);
        }else{
            redisTemplate.opsForValue().set(ZwzLoginProperties.USER_TOKEN_PRE + selectUser.getUsername(), ansUserToken, tokenProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(ZwzLoginProperties.HTTP_TOKEN_PRE + ansUserToken, new Gson().toJson(tokenUser), tokenProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
        }
        return ansUserToken;
    }

    @ApiOperation(value = "查询指定用户的权限列表")
    public List<GrantedAuthority> getCurrUserPerms(String userName){
        List<GrantedAuthority> ans = new ArrayList<>();
        User selectUser = selectByUserName(userName);
        if(selectUser == null){
            return ans;
        }
        List<PermissionDTO> perList = selectUser.getPermissions();
        if(perList.size() < 1) {
            return ans;
        }
        for(PermissionDTO vo : perList){
            ans.add(new SimpleGrantedAuthority(vo.getTitle()));
        }
        return ans;
    }

    @ApiOperation(value = "查询当前Token的用户对象")
    public User getCurrUser(){
        Object selectUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(Objects.equals("anonymousUser",selectUser.toString())){
            throw new ZwzException("登陆失效");
        }
        UserDetails user = (UserDetails) selectUser;
        return selectByUserName(user.getUsername());
    }
}
