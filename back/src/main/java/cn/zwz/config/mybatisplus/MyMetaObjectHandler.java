package cn.zwz.config.mybatisplus;

import cn.zwz.common.redis.RedisTemplateHelper;
import cn.zwz.modules.base.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 字段填充审计
 * @author 郑为中
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!"anonymousUser".equals(principal.toString())){
                UserDetails user = (UserDetails) principal;
                String str = redisTemplate.get("OAUSER:" + user.getUsername());
                if(str != null) {
                    this.setFieldValByName("createBy", str, metaObject);
                } else {
                    this.setFieldValByName("createBy", user.getUsername(), metaObject);
                }
            }
            this.setFieldValByName("createTime", new Date(), metaObject);
        }
        catch (NullPointerException e) {
            this.setFieldValByName("createBy", "API接口", metaObject);
            this.setFieldValByName("createTime", new Date(), metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!"anonymousUser".equals(principal.toString())){
                UserDetails user = (UserDetails) principal;
                this.setFieldValByName("updateBy", user.getUsername(), metaObject);
                String str = redisTemplate.get("OAUSER:" + user.getUsername());
                if(str != null) {
                    this.setFieldValByName("updateBy", str, metaObject);
                } else {
                    this.setFieldValByName("updateBy", user.getUsername(), metaObject);
                }
            }
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }
        catch (NullPointerException e) {
            this.setFieldValByName("updateBy", "API接口", metaObject);
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}

