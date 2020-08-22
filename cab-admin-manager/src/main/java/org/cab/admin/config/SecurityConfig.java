package org.cab.admin.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.cab.api.admin.constan.AdminConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {

    /**
     * shiro-spring-boot-web-starter 需要依赖
     *
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        return new DefaultShiroFilterChainDefinition();
    }

    /**
     * @return shiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //配置拦截规则 (map用错是个天坑)
        Map<String, String> filterChainDefinitionsMap = new LinkedHashMap<>();
        //anon 无需认证
        filterChainDefinitionsMap.put(AdminConst.Security.STATIC_URL, "anon");
        //登录映射url
        filterChainDefinitionsMap.put(AdminConst.Security.DOLOGIN_URL, "anon");
        //logout 登出功能 shiro实现
        filterChainDefinitionsMap.put(AdminConst.Security.LOGOUT_URL, "logout");
        //authc 拦截会让rememberme功能失效 开启需要改成user权限
        filterChainDefinitionsMap.put(AdminConst.Security.FILTER_URL, "user");
        //authc 无认证全部拦截
       // filterChainDefinitionsMap.put(AdminConst.Security.FILTER_URL, "authc");


        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionsMap);
        shiroFilterFactoryBean.setLoginUrl(AdminConst.Security.LOGIN_URL);
        shiroFilterFactoryBean.setSuccessUrl(AdminConst.Security.ROOT_URL);
        shiroFilterFactoryBean.setUnauthorizedUrl(AdminConst.Security.UNAUTHORIZED_URL);
        return shiroFilterFactoryBean;
    }

    /**
     * @return SecurityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入自定义领域类
        securityManager.setRealm(userRealm());
        //注入cookie管理器
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;

    }

    /**
     * @return userRealm
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        //设置密码比较器
        userRealm.setCredentialsMatcher(customHashedCredentialsMatcher());
        return userRealm;
    }

    /**
     * 密码加密比较器
     *
     * @return
     */
    @Bean
    public CredentialsMatcher customHashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //加密算法 与数据库加密存储的要一致
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //密码哈希次数 与数据库加密存储的哈希次数要一致
        hashedCredentialsMatcher.setHashIterations(AdminConst.Security.PASSWORD_ENCRYPTION_COUNT);
        //16进制 存储
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * cookie对象
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //登录页 checkBox 对应的name
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        //cookie过期时间 单位秒  10天
        cookie.setMaxAge(43200);
        return cookie;
    }

    /**
     * cookie 管理对象
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        //设置cookie
        rememberMeManager.setCookie(rememberMeCookie());
        //cookie 加密密匙 默认AES算法 密钥长度(128 256 512 位)
        rememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return rememberMeManager;

    }
    /**
     * 页面标签库支持
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}