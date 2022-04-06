package com.example.authority.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author MrBird
 */
@Data
@SpringBootConfiguration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "auth.swagger")
public class SwaggerProperties {
    private String basePackage;
    private String title;
    private String description;
    private String version;
    private String author;
    private String url;
    private String email;
    private String license;
    private String licenseUrl;
}
