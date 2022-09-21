package cn.zwz.basics.baseClass;

import cn.zwz.basics.redis.RedisTemplateHelper;
import cn.zwz.data.entity.User;
import cn.zwz.data.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

public class StartBean implements ApplicationRunner {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    private static final String REDIS_USER_PRE = "USER:";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> userList = iUserService.list();
        int errorNumber = 0;
        for (User user : userList) {
            if(user.getNickname() != null && user.getUsername() != null) {
                redisTemplate.set(REDIS_USER_PRE + user.getUsername(), user.getNickname());
            }
        }
    }
}
