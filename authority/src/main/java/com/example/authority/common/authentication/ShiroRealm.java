package com.example.authority.common.authentication;

import com.example.authority.common.properties.AuthProperties;
import com.example.authority.domain.entity.User;
import com.example.authority.service.ISessionService;
import com.example.authority.service.IUserDataPermissionService;
import com.example.authority.service.IUserService;
import com.example.authority.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 *
 * @author MrBird
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ShiroRealm extends AuthorizingRealm {

    private final ISessionService sessionService;
    private final ShiroLogoutService shiroLogoutService;
    private final IUserDataPermissionService userDataPermissionService;
    private final IUserService userService;

    private RedisCacheManager redisCacheManager;
    private EhCacheManager ehCacheManager;
    @Value("${" + AuthProperties.ENABLE_REDIS_CACHE + "}")
    private boolean enableRedisCache;

    @Autowired(required = false)
    public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    @Autowired(required = false)
    public void setEhCacheManager(EhCacheManager ehCacheManager) {
        this.ehCacheManager = ehCacheManager;
    }

    @PostConstruct
    private void initConfig() {
        setAuthenticationCachingEnabled(false);
        setAuthorizationCachingEnabled(true);
        setCachingEnabled(true);
        setCacheManager(redisCacheManager == null ? ehCacheManager : redisCacheManager);
    }

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof BearerToken;
    }

    /**
     * 授权模块，获取用户角色和权限
     *
     * @param principal principal
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        String username = JWTUtil.getUsername(principal.toString());
        User user = new User();
        user.setUsername(username);
        userService.doGetUserAuthorizationInfo(user);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(user.getRoles());
        simpleAuthorizationInfo.setStringPermissions(user.getStringPermissions());
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param auth AuthenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String token = (String) auth.getPrincipal();
        String username = JWTUtil.getUsername(token);

        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("Login expired!");
        }

        // 通过用户名到数据库查询用户信息
        User user = this.userService.findByName(username);

        if (user == null) {
            throw new IncorrectCredentialsException("Username does not exist!");
        }

        if (!JWTUtil.verify(token, username, user.getPassword())) {
            throw new AuthenticationException("Wrong user name or password!");
        }

        if (User.STATUS_LOCK.equals(user.getStatus())) {
            throw new LockedAccountException("The account has been locked, please contact the administrator!");
        }
        String deptIds = this.userDataPermissionService.findByUserId(String.valueOf(user.getUserId()));
        user.setDeptIds(deptIds);
        return new SimpleAuthenticationInfo(user, token, getName());
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        super.onLogout(principals);
        if (enableRedisCache) {
            shiroLogoutService.cleanCacheFragment(principals);
        }
    }

    public void clearCache(Long userId) {
        List<SimplePrincipalCollection> principals = sessionService.getPrincipals(userId);
        if (CollectionUtils.isNotEmpty(principals)) {
            for (SimplePrincipalCollection principal : principals) {
                super.clearCache(principal);
            }
        }
    }
}
