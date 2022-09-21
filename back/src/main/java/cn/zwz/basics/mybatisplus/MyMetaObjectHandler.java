package cn.zwz.basics.mybatisplus;

import cn.zwz.basics.redis.RedisTemplateHelper;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * MybatisPlus字段填充
 * @author 郑为中
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private RedisTemplateHelper redisTemplate;

    private static final String ANONYMOUS_USER = "anonymousUser";

    private static final String REDIS_PRE = "OAUSER:";

    private static final String CREATE_BY = "createBy";

    private static final String CREATE_TIME = "createTime";

    private static final String UPDATE_BY = "updateBy";

    private static final String UPDATE_TIME = "updateTime";

    private static final String DEFALUT_STR = "API接口";

    @Override
    @ApiOperation(value = "新增填充")
    public void insertFill(MetaObject metaObject) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!Objects.equals(ANONYMOUS_USER,principal.toString())){
                UserDetails user = (UserDetails) principal;
                String str = redisTemplate.get(REDIS_PRE + user.getUsername());
                if(str != null) {
                    this.setFieldValByName(CREATE_BY, str, metaObject);
                } else {
                    this.setFieldValByName(CREATE_BY, user.getUsername(), metaObject);
                }
            }
            this.setFieldValByName(CREATE_TIME, new Date(), metaObject);
        }
        catch (NullPointerException e) {
            this.setFieldValByName(CREATE_BY, DEFALUT_STR, metaObject);
            this.setFieldValByName(CREATE_TIME, new Date(), metaObject);
        }
    }

    @Override
    @ApiOperation(value = "编辑填充")
    public void updateFill(MetaObject metaObject) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!Objects.equals(ANONYMOUS_USER,principal.toString())){
                UserDetails user = (UserDetails) principal;
                this.setFieldValByName(UPDATE_BY, user.getUsername(), metaObject);
                String str = redisTemplate.get(REDIS_PRE + user.getUsername());
                if(str != null) {
                    this.setFieldValByName(UPDATE_BY, str, metaObject);
                } else {
                    this.setFieldValByName(UPDATE_BY, user.getUsername(), metaObject);
                }
            }
            this.setFieldValByName(UPDATE_TIME, new Date(), metaObject);
        }
        catch (NullPointerException e) {
            this.setFieldValByName(UPDATE_BY, DEFALUT_STR, metaObject);
            this.setFieldValByName(UPDATE_TIME, new Date(), metaObject);
        }
    }
}

