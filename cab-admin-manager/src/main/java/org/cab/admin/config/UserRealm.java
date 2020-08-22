package org.cab.admin.config;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.cab.api.authen.service.UserAuthenService;
import org.cab.api.user.module.User;


public class UserRealm extends AuthorizingRealm {
    @Reference
    private UserAuthenService userAuthenService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证 登录
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //数据库查询用户 用户密码是否匹配交由shiro认证
        User user = userAuthenService.selectUserByUserName(username);
        if (user == null) {
            //return null shiro抛异常
            return null;
        }
        //md5加密加盐
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
    }
}
