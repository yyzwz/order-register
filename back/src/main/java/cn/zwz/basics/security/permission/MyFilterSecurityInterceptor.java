package cn.zwz.basics.security.permission;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 用户行为监控过滤类
 * @author 郑为中
 */
@Slf4j
@Component
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager manager) {
        super.setAccessDecisionManager(manager);
    }

    @Override
    public void destroy() {
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.filterInvocationSecurityMetadataSource;
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    @ApiOperation(value = "权限过滤")
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        invoke(new FilterInvocation(request, response, filterChain));
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    public void invoke(FilterInvocation filterInvocation) {
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } catch (IOException e) {
            log.warn(e.toString());
        } catch (ServletException e) {
            log.warn(e.toString());
        } finally {
            super.afterInvocation(token, null);
        }
    }
}
