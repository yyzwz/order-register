package cn.zwz.data.controller;

import cn.zwz.basics.log.SystemLog;
import cn.zwz.basics.redis.RedisTemplateHelper;
import cn.zwz.basics.utils.*;
import cn.zwz.basics.security.SecurityUserDetails;
import cn.zwz.basics.parameter.CommonConstant;
import cn.zwz.basics.log.LogType;
import cn.zwz.basics.exception.ZwzException;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.data.entity.*;
import cn.zwz.data.service.*;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.data.vo.PermissionDTO;
import cn.zwz.data.vo.RoleDTO;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户
 * @author 郑为中
 */
@RestController
@Api(tags = "用户接口")
@RequestMapping("/zwz/user")
@CacheConfig(cacheNames = "user")
@Transactional
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDepartmentService iDepartmentService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IUserRoleService iUserRoleService;

    @Autowired
    private IDepartmentHeaderService iDepartmentHeaderService;

    @Autowired
    private IRolePermissionService iRolePermissionService;

    @Autowired
    private RedisTemplateHelper redisTemplateHelper;

    @Autowired
    private IPermissionService iPermissionService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String REDIS_PRE_1 = "userRole::";

    private static final String REDIS_PRE_2 = "userRole::depIds:";

    private static final String REDIS_PRE_3 = "permission::userMenuList:";

    private static final String REDIS_PRE_4 = "user::";

    @SystemLog(about = "获取当前登录用户", type = LogType.DATA_CENTER,doType = "USER-02")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户")
    public Result<User> getUserInfo(){
        User u = securityUtil.getCurrUser();
        entityManager.clear();
        u.setPassword(null);
        return new ResultUtil<User>().setData(u);
    }

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户")
    public Result<Object> regist(@Valid User u){
        u.setEmail(u.getMobile() + "@qq.com");
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.and(wrapper -> wrapper.eq("username", u.getUsername()).or().eq("mobile",u.getMobile()));
        if(iUserService.count(userQw) > 0L) {
            return ResultUtil.error("登陆账号/手机号重复");
        }
        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass).setType(0);
        iUserService.saveOrUpdate(u);
        QueryWrapper<Role> roleQw = new QueryWrapper<>();
        roleQw.eq("default_role",true);
        List<Role> roleList = iRoleService.list(roleQw);
        if(roleList.size() > 0){
            for(Role role : roleList) {
                iUserRoleService.saveOrUpdate(new UserRole().setUserId(u.getId()).setRoleId(role.getId()));
            }
        }
        return ResultUtil.data(u);
    }

    @SystemLog(about = "解锁验证密码", type = LogType.DATA_CENTER,doType = "USER-03")
    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    @ApiOperation(value = "解锁验证密码")
    public Result<Object> unLock(@RequestParam String password){
        User u = securityUtil.getCurrUser();
        if(!new BCryptPasswordEncoder().matches(password, u.getPassword())){
            return ResultUtil.error("密码不正确");
        }
        return ResultUtil.data(null);
    }

    @SystemLog(about = "重置密码", type = LogType.DATA_CENTER,doType = "USER-04")
    @RequestMapping(value = "/resetPass", method = RequestMethod.POST)
    @ApiOperation(value = "重置密码")
    public Result<Object> resetPass(@RequestParam String[] ids){
        for(String id : ids){
            User userForReset = iUserService.getById(id);
            if(userForReset == null) {
                return ResultUtil.error("不存在");
            }
            userForReset.setPassword(new BCryptPasswordEncoder().encode("123456"));
            iUserService.saveOrUpdate(userForReset);
            redisTemplate.delete(REDIS_PRE_4 + userForReset.getUsername());
        }
        return ResultUtil.success();
    }

    @SystemLog(about = "修改用户资料", type = LogType.DATA_CENTER,doType = "USER-05")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户资料",notes = "用户名密码不会修改 需要username更新缓存")
    @CacheEvict(key = "#u.username")
    public Result<Object> editOwn(User u){
        User old = securityUtil.getCurrUser();
        u.setUsername(old.getUsername());
        u.setPassword(old.getPassword());
        iUserService.saveOrUpdate(u);
        return ResultUtil.success("修改成功");
    }

    @SystemLog(about = "修改密码", type = LogType.DATA_CENTER,doType = "USER-06")
    @RequestMapping(value = "/modifyPass", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码")
    public Result<Object> modifyPass(@RequestParam String password,@RequestParam String newPass,@RequestParam String passStrength){
        User user = securityUtil.getCurrUser();
        if(!new BCryptPasswordEncoder().matches(password, user.getPassword())){
            return ResultUtil.error("原密码不正确");
        }
        String newEncryptPass= new BCryptPasswordEncoder().encode(newPass);
        user.setPassword(newEncryptPass);
        user.setPassStrength(passStrength);
        iUserService.saveOrUpdate(user);
        redisTemplate.delete(REDIS_PRE_4 + user.getUsername());
        return ResultUtil.success();
    }

    @SystemLog(about = "查询用户", type = LogType.DATA_CENTER,doType = "USER-07")
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户")
    public Result<IPage<User>> getUserList(@ModelAttribute User user, @ModelAttribute PageVo page) {
        QueryWrapper<User> userQw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(user.getNickname())) {
            userQw.like("nickname",user.getNickname());
        }
        if(!ZwzNullUtils.isNull(user.getDepartmentId())) {
            userQw.eq("department_id",user.getDepartmentId());
        }
        IPage<User> userData = iUserService.page(PageUtil.initMpPage(page),userQw);
        for(User u: userData.getRecords()) {
            QueryWrapper<Role> roleQw = new QueryWrapper<>();
            roleQw.inSql("id","SELECT role_id FROM a_user_role WHERE user_id = '" + u.getId() + "'");
            List<Role> list = iRoleService.list(roleQw);
            List<RoleDTO> roleDTOList = list.stream().map(e->{
                return new RoleDTO().setId(e.getId()).setName(e.getName()).setDescription(e.getDescription());
            }).collect(Collectors.toList());
            u.setRoles(roleDTOList);
            entityManager.detach(u);
            u.setPassword(null);
        }
        return new ResultUtil<IPage<User>>().setData(userData);
    }

    @SystemLog(about = "根据部门查询用户", type = LogType.DATA_CENTER,doType = "USER-08")
    @RequestMapping(value = "/getByDepartmentId", method = RequestMethod.GET)
    @ApiOperation(value = "根据部门查询用户")
    public Result<List<User>> getByCondition(@RequestParam String departmentId){
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("department_id", departmentId);
        List<User> list = iUserService.list(userQw);
        entityManager.clear();
        list.forEach(u -> {
            u.setPassword(null);
        });
        return new ResultUtil<List<User>>().setData(list);
    }

    @SystemLog(about = "模拟搜索用户", type = LogType.DATA_CENTER,doType = "USER-09")
    @RequestMapping(value = "/searchByName/{username}", method = RequestMethod.GET)
    @ApiOperation(value = "模拟搜索用户")
    public Result<List<User>> searchByName(@PathVariable String username) throws UnsupportedEncodingException {
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("username", URLDecoder.decode(username, "utf-8"));
        userQw.eq("status", 0);
        List<User> list = iUserService.list(userQw);
        entityManager.clear();
        list.forEach(u -> {
            u.setPassword(null);
        });
        return new ResultUtil<List<User>>().setData(list);
    }

    @SystemLog(about = "查询全部用户", type = LogType.DATA_CENTER,doType = "USER-10")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部用户")
    public Result<List<User>> getAll(){
        List<User> userList = iUserService.list();
        for(User user: userList){
            entityManager.clear();
            user.setPassword(null);
        }
        return new ResultUtil<List<User>>().setData(userList);
    }

    @SystemLog(about = "管理员修改资料", type = LogType.DATA_CENTER,doType = "USER-11")
    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    @ApiOperation(value = "管理员修改资料")
    @CacheEvict(key = "#u.username")
    public Result<Object> edit(User u,@RequestParam(required = false) String[] roleIds){
        User customaryUser = iUserService.getById(u.getId());
        // 登陆账号和密码不能发生变更
        u.setUsername(customaryUser.getUsername());
        u.setPassword(customaryUser.getPassword());
        if(!Objects.equals(customaryUser.getMobile(),u.getMobile())) {
            QueryWrapper<User> customaryUserQw = new QueryWrapper<>();
            customaryUserQw.ne("id",customaryUser.getId());
            customaryUserQw.eq("mobile",u.getMobile());
            long customaryUserCount = iUserService.count(customaryUserQw);
            if(customaryUserCount > 0) {
                return ResultUtil.error("手机号重复");
            }
        }
        if(!ZwzNullUtils.isNull(u.getDepartmentId())) {
            Department department = iDepartmentService.getById(u.getDepartmentId());
            if(department != null) {
                u.setDepartmentTitle(department.getTitle());
            }
        } else {
            u.setDepartmentId("");
            u.setDepartmentTitle("");
        }
        // 吃哦就花
        iUserService.saveOrUpdate(u);
        QueryWrapper<UserRole> userRoleQw = new QueryWrapper<>();
        userRoleQw.eq("user_id",u.getId());
        iUserRoleService.remove(userRoleQw);
        if(roleIds != null && roleIds.length > 0) {
            for (String roleId : roleIds) {
                UserRole ur = new UserRole();
                ur.setUserId(u.getId());
                ur.setRoleId(roleId);
                iUserRoleService.saveOrUpdate(ur);
            }
        }
        redisTemplate.delete(REDIS_PRE_1 + u.getId());
        redisTemplate.delete(REDIS_PRE_2 + u.getId());
        redisTemplate.delete(REDIS_PRE_3 + u.getId());
        return ResultUtil.success();
    }

    @SystemLog(about = "添加用户", type = LogType.DATA_CENTER,doType = "USER-12")
    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    public Result<Object> add(@Valid User u,@RequestParam(required = false) String[] roleIds) {
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.and(wrapper -> wrapper.eq("username", u.getUsername()).or().eq("mobile",u.getMobile()));
        if(iUserService.count(userQw) > 0L) {
            return ResultUtil.error("登陆账号/手机号重复");
        }
        if(!ZwzNullUtils.isNull(u.getDepartmentId())){
            Department department = iDepartmentService.getById(u.getDepartmentId());
            if(department != null){
                u.setDepartmentTitle(department.getTitle());
            }
        }else{
            u.setDepartmentId("");
            u.setDepartmentTitle("");
        }
        u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
        iUserService.saveOrUpdate(u);
        if(roleIds != null && roleIds.length > 0) {
            for (String roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(u.getId());
                userRole.setRoleId(roleId);
                iUserRoleService.saveOrUpdate(userRole);
            }
        }
        return ResultUtil.success();
    }

    @SystemLog(about = "禁用用户", type = LogType.DATA_CENTER,doType = "USER-13")
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @ApiOperation(value = "禁用用户")
    public Result<Object> disable( @RequestParam String id){
        User user = iUserService.getById(id);
        if(user == null){
            return ResultUtil.error("用户不存在");
        }
        user.setStatus(CommonConstant.USER_STATUS_LOCK);
        iUserService.saveOrUpdate(user);
        redisTemplate.delete("user::"+user.getUsername());
        return ResultUtil.success();
    }

    @SystemLog(about = "启用用户", type = LogType.DATA_CENTER,doType = "USER-14")
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    @ApiOperation(value = "启用用户")
    public Result<Object> enable(@RequestParam String id){
        User user = iUserService.getById(id);
        if(user==null){
            return ResultUtil.error("用户不存在");
        }
        user.setStatus(CommonConstant.USER_STATUS_NORMAL);
        iUserService.saveOrUpdate(user);
        redisTemplate.delete("user::"+user.getUsername());
        return ResultUtil.success();
    }

    @SystemLog(about = "删除用户", type = LogType.DATA_CENTER,doType = "USER-15")
    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for(String id:ids){
            User u = iUserService.getById(id);
            redisTemplate.delete("user::" + u.getUsername());
            redisTemplate.delete("userRole::" + u.getId());
            redisTemplate.delete("userRole::depIds:" + u.getId());
            redisTemplate.delete("permission::userMenuList:" + u.getId());
            Set<String> keys = redisTemplateHelper.keys("department::*");
            redisTemplate.delete(keys);
            iUserService.removeById(id);
            QueryWrapper<UserRole> urQw = new QueryWrapper<>();
            urQw.eq("user_id",id);
            iUserRoleService.remove(urQw);
            QueryWrapper<DepartmentHeader> dhQw = new QueryWrapper<>();
            dhQw.eq("user_id",id);
            iDepartmentHeaderService.remove(dhQw);
        }
        return ResultUtil.success();
    }

    @SystemLog(about = "导入用户", type = LogType.DATA_CENTER,doType = "USER-16")
    @RequestMapping(value = "/importData", method = RequestMethod.POST)
    @ApiOperation(value = "导入用户")
    public Result<Object> importData(@RequestBody List<User> users){
        List<Integer> errors = new ArrayList<>();
        List<String> reasons = new ArrayList<>();
        int count = 0;
        for(User u: users){
            count++;
            if(StrUtil.isBlank(u.getUsername())||StrUtil.isBlank(u.getPassword())){
                errors.add(count);
                reasons.add("账号密码为空");
                continue;
            }

            QueryWrapper<User> userQw = new QueryWrapper<>();
            userQw.eq("username",u.getUsername());
            if(iUserService.count(userQw) > 0L) {
                errors.add(count);
                reasons.add("用户名已存在");
                continue;
            }
            u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
            if(StrUtil.isNotBlank(u.getDepartmentId())){
                Department department = iDepartmentService.getById(u.getDepartmentId());
                if(department == null) {
                    errors.add(count);
                    reasons.add("部门不存在");
                    continue;
                }
            }
            if(u.getStatus()==null){
                u.setStatus(CommonConstant.USER_STATUS_NORMAL);
            }
            iUserService.saveOrUpdate(u);
            if(u.getDefaultRole() != null && u.getDefaultRole()==1) {
                QueryWrapper<Role> roleQw = new QueryWrapper<>();
                roleQw.eq("default_role",true);
                List<Role> roleList = iRoleService.list(roleQw);
                if(roleList!=null&&roleList.size()>0){
                    for(Role role : roleList){
                        UserRole ur = new UserRole().setUserId(u.getId()).setRoleId(role.getId());
                        iUserRoleService.saveOrUpdate(ur);
                    }
                }
            }
        }
        int successCount = users.size() - errors.size();
        String successMessage = "成功导入 " + successCount + " 位用户";
        String failMessage = "成功导入 " + successCount + " 位用户，失败 " + errors.size() + " 位用户。<br>" +"第 " + errors.toString() + " 行数据导入出错，错误原因是为 <br>" + reasons.toString();
        String message = null;
        if(errors.size() == 0){
            message = successMessage;
        }else{
            message = failMessage;
        }
        return ResultUtil.success(message);
    }

    @ApiOperation(value = "添加用户的角色和菜单信息")
    public User userToDTO(User user) {
        if(user == null) {
            return null;
        }
        // 角色
        QueryWrapper<UserRole> urQw = new QueryWrapper<>();
        urQw.eq("user_id", user.getId());
        List<UserRole> roleList = iUserRoleService.list(urQw);
        List<RoleDTO> roleDTOList = new ArrayList<>();
        for (UserRole userRole : roleList) {
            Role role = iRoleService.getById(userRole.getRoleId());
            if(role != null) {
                roleDTOList.add(new RoleDTO().setId(role.getId()).setName(role.getName()));
            }
        }
        user.setRoles(roleDTOList);
        // 菜单
        List<String> permissionIdList = new ArrayList<>();
        for (RoleDTO dto : roleDTOList) {
            QueryWrapper<RolePermission> rpQw = new QueryWrapper<>();
            rpQw.eq("role_id",dto.getId());
            List<RolePermission> list = iRolePermissionService.list(rpQw);
            for (RolePermission rp : list) {
                boolean flag = true;
                for (String id : permissionIdList) {
                    if(Objects.equals(id,rp.getPermissionId())) {
                        flag = false;
                        break;
                    }
                }
                if(flag) {
                    permissionIdList.add(rp.getPermissionId());
                }
            }
        }
        List<PermissionDTO> permissionDTOList = new ArrayList<>();
        for (String id : permissionIdList) {
            Permission permission = iPermissionService.getById(id);
            if(permission != null) {
                if(Objects.equals(permission.getType(),CommonConstant.PERMISSION_OPERATION)) {
                    continue;
                }
                permissionDTOList.add(new PermissionDTO().setTitle(permission.getTitle()).setPath(permission.getPath()));
            }
        }
        user.setPermissions(permissionDTOList);
        return user;
    }
}
