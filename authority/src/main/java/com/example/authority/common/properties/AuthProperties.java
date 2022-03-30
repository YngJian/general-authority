package com.example.authority.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author MrBird
 */
@Data
@SpringBootConfiguration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = AuthProperties.PROPERTIES_PREFIX)
public class AuthProperties {

    public static final String PROPERTIES_PREFIX = "auth";
    public static final String ENABLE_REDIS_CACHE = "auth.enable-redis-cache";

    private ShiroProperties shiro = new ShiroProperties();
    private SwaggerProperties swagger = new SwaggerProperties();
    /**
     * 批量插入提交commit数据量
     */
    private int maxBatchInsertNum = 1000;

    /**
     * 是否开启Redis缓存，true开启，false关闭
     * 为false时，采用基于内存的ehcache缓存
     */
    private boolean enableRedisCache;
}
