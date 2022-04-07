package com.example.authority;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yangj
 */
@SpringBootApplication(scanBasePackages = {"com.example.authority", "com.vayne.security"})
@MapperScan("com.example.authority.domain.mapper")

public class AuthorityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
    }

}
