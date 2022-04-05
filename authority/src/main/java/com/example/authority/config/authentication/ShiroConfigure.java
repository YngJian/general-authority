package com.example.authority.config.authentication;

import com.example.authority.common.annotation.ConditionOnRedisCache;
import com.example.authority.common.entity.Strings;
import com.example.authority.common.properties.AuthProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

/**
 * Shiro 配置类
 *
 * @author MrBird
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class ShiroConfigure {

    private final AuthProperties authProperties;
    private RedisProperties redisProperties;
    /**
     * remember key
     * 生成方式：
     * String encryptKey = RandomStringUtils.randomAlphanumeric(15);
     * byte[] encryptKeyBytes = encryptKey.getBytes(StandardCharsets.UTF_8);
     * String rememberKey = Base64Utils.encodeToString(Arrays.copyOf(encryptKeyBytes, 16));
     */
    private final static String REMEMBER_ME_KEY = "M1RIN2FVNGt6T2lRU2VNAA==";

    @Autowired(required = false)
    public void setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    /**
     * shiro 中配置 redis 缓存
     *
     * @return RedisManager
     */
    private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisProperties.getHost() + Strings.COLON + redisProperties.getPort());
        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            redisManager.setPassword(redisProperties.getPassword());
        }
        redisManager.setTimeout(redisManager.getTimeout());
        redisManager.setDatabase(redisProperties.getDatabase());
        return redisManager;
    }

    @Bean
    @ConditionOnRedisCache
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        // 权限缓存超时时间，和session超时时间一致
        redisCacheManager.setExpire((int) authProperties.getShiro().getSessionTimeout().getSeconds());
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    @Bean
    @ConditionalOnMissingBean(RedisCacheManager.class)
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");
        return ehCacheManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm shiroRealm,
                                                     @Nullable RedisCacheManager redisCacheManager,
                                                     @Nullable EhCacheManager ehCacheManager,
                                                     DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 SecurityManager，并注入 shiroRealm
        securityManager.setRealm(shiroRealm);
        // 配置 shiro session管理器
        securityManager.setSessionManager(sessionManager);
        // 配置 缓存管理类 cacheManager
        securityManager.setCacheManager(redisCacheManager != null ? redisCacheManager : ehCacheManager);
        // 配置 rememberMeCookie
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * rememberMe cookie 效果是重开浏览器后无需重新登录
     *
     * @return SimpleCookie
     */
    private SimpleCookie rememberMeCookie() {
        // 设置 cookie 名称，对应 login.html 页面的 <input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置 cookie 的过期时间，单位为秒
        cookie.setMaxAge((int) authProperties.getShiro().getCookieTimeout().getSeconds());
        return cookie;
    }

    /**
     * cookie管理对象
     *
     * @return CookieRememberMeManager
     */
    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode(REMEMBER_ME_KEY));
        return cookieRememberMeManager;
    }

    @Bean
    @ConditionOnRedisCache
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    @ConditionalOnMissingBean(RedisSessionDAO.class)
    public MemorySessionDAO memorySessionDAO() {
        return new MemorySessionDAO();
    }

    /**
     * session 管理对象
     *
     * @return DefaultWebSessionManager
     */
    @Bean
    public DefaultWebSessionManager sessionManager(@Nullable RedisSessionDAO redisSessionDAO,
                                                   @Nullable MemorySessionDAO memorySessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置 session超时时间
        sessionManager.setGlobalSessionTimeout(authProperties.getShiro().getSessionTimeout().toMillis());
        sessionManager.setSessionDAO(redisSessionDAO == null ? memorySessionDAO : redisSessionDAO);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
}

