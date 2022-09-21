package cn.zwz.basics.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.zwz.basics.redis.RedisTemplateHelper;
import cn.zwz.basics.utils.IpInfoUtil;
import cn.zwz.basics.utils.ThreadPoolUtil;
import cn.zwz.data.entity.Log;
import cn.zwz.data.service.LogService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Spring AOP实现日志管理
 * @author 郑为中
 */
@Aspect
@Component
@Slf4j
public class SystemLogAspect {

    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");

    @Autowired
    private LogService logService;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    private static final String REDIS_USER_PRE = "USER:";

    private static final String LOG_DES_PRE = "description";

    private static final String LOG_TYPE_PRE = "type";

    private static final String LOG_DO_PRE = "doType";

    @ApiOperation(value = "控制层切点")
    @Pointcut("@annotation(cn.zwz.basics.log.SystemLog)")
    public void controllerAspect() {

    }

    @ApiOperation(value = "前置通知")
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);
    }

    @ApiOperation(value = "后置通知")
    @AfterReturning("controllerAspect()")
    public void after(JoinPoint joinPoint){
        try {
            String username = "";
            String description = getControllerMethodInfo(joinPoint).get("description").toString();
            int type = (int)getControllerMethodInfo(joinPoint).get("type");
            String doType = getControllerMethodInfo(joinPoint).get("doType").toString();
            Map<String, String[]> logParams = request.getParameterMap();
            JSONObject logJo = new JSONObject();
            for (Map.Entry<String, String[]> keyInMap : logParams.entrySet()) {
                String keyItemInMap = keyInMap.getKey();
                String paramValue = (keyInMap.getValue() != null && keyInMap.getValue().length > 0 ? keyInMap.getValue()[0] : "");
                logJo.put(keyItemInMap,StrUtil.endWithIgnoreCase(keyInMap.getKey(), "password") ? "" : paramValue);
            }
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(Objects.equals("anonymousUser",principal.toString())){
                return;
            }
            String device = "", isMobile = "PC端";
            UserAgent ua = UserAgentUtil.parse(request.getHeader("user-agent"));
            if (ua != null) {
                if (ua.isMobile()) {
                    isMobile = "移动端";
                }
                device = isMobile + " | " + ua.getBrowser().toString() + " " + ua.getVersion() + " | " + ua.getPlatform().toString() + " " + ua.getOs().toString();
            }
            UserDetails user = (UserDetails) principal;
            String str = redisTemplate.get(REDIS_USER_PRE + user.getUsername());
            if(str != null) {
                username = str;
            } else {
                username = user.getUsername();
            }
            Log log = new Log();
            log.setUsername(username);
            log.setName(description);
            log.setLogType(type);
            log.setCode(doType);
            log.setRequestUrl(request.getRequestURI());
            log.setRequestType(request.getMethod());
            log.setRequestParam(logJo.toJSONString());
            log.setIp(ipInfoUtil.getRequestIpAddress(request));
            log.setDevice(device);
            log.setIpInfo(ipInfoUtil.getIpCity(request));
            Long logElapsedTime = System.currentTimeMillis() - beginTimeThreadLocal.get().getTime();
            log.setCostTime(logElapsedTime.intValue());
            ThreadPoolUtil.getPool().execute(new SaveSystemLogThread(log, logService));
        } catch (Exception e) {
            log.error("日志异常", e);
        }
    }

    @ApiOperation(value = "保存日志")
    private static class SaveSystemLogThread implements Runnable {

        private Log log;
        private LogService logService;

        public SaveSystemLogThread(Log esLog, LogService logService) {
            this.log = esLog;
            this.logService = logService;
        }

        @Override
        public void run() {
            logService.save(log);
        }
    }

    public static Map<String, Object> getControllerMethodInfo(JoinPoint aopLogPoint) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>(16);
        Method[] methods = Class.forName(aopLogPoint.getTarget().getClass().getName()).getMethods();
        for(Method method : methods) {
            if(!Objects.equals(aopLogPoint.getSignature().getName(),method.getName())) {
                continue;
            }
            Class[] aopLogClass = method.getParameterTypes();
            if(aopLogClass.length != aopLogPoint.getArgs().length) {
                continue;
            }
            map.put(LOG_DO_PRE, method.getAnnotation(SystemLog.class).doType());
            map.put(LOG_DES_PRE, method.getAnnotation(SystemLog.class).about());
            map.put(LOG_TYPE_PRE, method.getAnnotation(SystemLog.class).type().ordinal());
        }
        return map;
    }
}
