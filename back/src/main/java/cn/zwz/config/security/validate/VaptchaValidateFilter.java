package cn.zwz.config.security.validate;

import cn.zwz.common.constant.SettingConstant;
import cn.zwz.common.utils.IpInfoUtil;
import cn.zwz.common.utils.ResponseUtil;
import cn.zwz.config.properties.CaptchaProperties;
import cn.zwz.modules.base.entity.Setting;
import cn.zwz.modules.base.service.SettingService;
import cn.zwz.modules.base.vo.VaptchaSetting;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图形验证码过滤器
 * @author 郑为中
 */
@Slf4j
@Configuration
public class VaptchaValidateFilter extends OncePerRequestFilter {

    @Autowired
    private CaptchaProperties captchaProperties;

    @Autowired
    private SettingService settingService;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @Autowired
    private PathMatcher pathMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 判断URL是否需要验证
        Boolean flag = false;
        String requestUrl = request.getRequestURI();
        for(String url : captchaProperties.getVaptcha()){
            if(pathMatcher.match(url, requestUrl)){
                flag = true;
                break;
            }
        }
        if(flag){
            String token = request.getParameter("token");
            if(StrUtil.isBlank(token)){
                ResponseUtil.out(response, ResponseUtil.resultMap(false,500,"请传入Vaptcha验证码所需参数token"));
                return;
            }
            Setting setting = settingService.get(SettingConstant.VAPTCHA_SETTING);
            if(StrUtil.isBlank(setting.getValue())){
                ResponseUtil.out(response, ResponseUtil.resultMap(false,500,"系统还未配置Vaptcha验证码，请联系管理员"));
                return;
            }
            VaptchaSetting vs = new Gson().fromJson(setting.getValue(), VaptchaSetting.class);
            // 验证vaptcha验证码
            String params = "id=" + vs.getVid() + "&secretkey=" + vs.getSecretKey() + "&token=" + token
                    + "&ip=" + ipInfoUtil.getIpAddr(request);
            String result = HttpUtil.post(SettingConstant.VAPTCHA_URL, params);
            if(!result.contains("\"success\":1")){
                ResponseUtil.out(response, ResponseUtil.resultMap(false,500,"Vaptcha验证码验证失败"));
                return;
            }
            // 验证成功 放行
            chain.doFilter(request, response);
            return;
        }
        // 无需验证 放行
        chain.doFilter(request, response);
    }
}
