package org.cab.admin.config;

import org.cab.admin.interceptor.SessionKeepAliveInterceptor;
import org.cab.api.admin.constan.AdminConst;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册session拦截器
 */
@Configuration
public class RegistryInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionKeepAliveInterceptor())
                .excludePathPatterns(AdminConst.Security.EXCLUDEURL);
    }
}


