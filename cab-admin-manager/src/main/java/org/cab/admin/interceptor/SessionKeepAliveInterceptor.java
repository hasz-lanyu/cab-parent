package org.cab.admin.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.cab.api.admin.constan.AdminConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * 实现用户活跃状态刷新session过期时间
 */
public class SessionKeepAliveInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(SessionKeepAliveInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

       if (log.isDebugEnabled()){
           log.debug("SessionKeepAliveInterceptor start....");
       }
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            subject.getSession().setTimeout(AdminConst.Security.SESSION_KEEP_ALIVE_TIME);
            if (log.isDebugEnabled()){
                log.debug("url:[{}]session refresh....",request.getRequestURI());
            }
        }
        return true;
    }


}
