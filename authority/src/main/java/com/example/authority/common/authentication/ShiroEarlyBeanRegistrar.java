package com.example.authority.common.authentication;

import com.example.authority.common.Constant.AuthConstant;
import com.example.authority.common.entity.Strings;
import com.example.authority.common.filter.JWTFilter;
import com.example.authority.common.properties.AuthProperties;
import com.example.authority.common.properties.ShiroProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 将注册时机较早的Bean单独提取出来，并且相关依赖延迟注入，仅可能的缩小
 * 对Bean后置处理器的影响
 * <p>
 * https://github.com/spring-projects/spring-boot/issues/16097
 * https://issues.apache.org/jira/browse/SHIRO-743
 *
 * @author MrBird
 */
@Configuration(proxyBeanMethods = false)
public class ShiroEarlyBeanRegistrar {

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Lazy DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Lazy DefaultWebSecurityManager securityManager,
                                                         @Lazy AuthProperties authProperties) {
        ShiroProperties shiro = authProperties.getShiro();
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JWTFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        // 登录的 url
        shiroFilterFactoryBean.setLoginUrl(shiro.getLoginUrl());
        // 登录成功后跳转的 url
        shiroFilterFactoryBean.setSuccessUrl(shiro.getSuccessUrl());
        // 未授权 url
        shiroFilterFactoryBean.setUnauthorizedUrl(shiro.getUnauthorizedUrl());
        // 设置免认证 url
        LinkedHashMap<String, String> filterChainDefinitionMap;
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(shiro.getAnonUrl(), Strings.COMMA);
        filterChainDefinitionMap = Arrays.stream(anonUrls).collect(Collectors.toMap(url -> url, url -> "anon", (a, b) -> b, LinkedHashMap::new));
        // 配置退出过滤器，其中具体的退出代码 Shiro已经替我们实现了
        filterChainDefinitionMap.put(shiro.getLogoutUrl(), "logout");
        // 除上以外所有 url都必须认证通过才可以访问，未通过认证自动访问 LoginUrl
        filterChainDefinitionMap.put(AuthConstant.REQUEST_ALL, "jwt");
        // 所有请求通过我们自己的JWT Filter
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}
