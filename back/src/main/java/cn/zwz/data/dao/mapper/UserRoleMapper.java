package cn.zwz.data.dao.mapper;

import cn.zwz.data.entity.Role;
import cn.zwz.data.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户-角色关系 数据链路层接口
 * @author 郑为中
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
