package com.example.authority.controller;

import com.example.authority.common.annotation.Limit;
import com.example.authority.common.entity.AuthResponse;
import com.example.authority.common.properties.AuthProperties;
import com.example.authority.domain.entity.User;
import com.example.authority.service.ILoginLogService;
import com.example.authority.service.IUserService;
import com.example.authority.utils.Md5Util;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author MrBird
 */
@Validated
@RestController
@RequiredArgsConstructor
public class LoginController extends BaseController {
    private final IUserService userService;
    private final ILoginLogService loginLogService;
    private final AuthProperties properties;

    @PostMapping("login")
    @Limit(key = "login", period = 60, count = 10, name = "登录接口", prefix = "limit")
    public AuthResponse login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password,
            boolean rememberMe) throws RuntimeException {
        UsernamePasswordToken token = new UsernamePasswordToken(username,
                Md5Util.encrypt(username.toLowerCase(), password), rememberMe);
        super.login(token);
        // 保存登录日志
        loginLogService.saveLoginLog(username);
        return new AuthResponse().success().data(properties.getShiro().getSuccessUrl());
    }

    @PostMapping("register")
    public AuthResponse register(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws RuntimeException {
        User user = userService.findByName(username);
        if (user != null) {
            throw new RuntimeException("该用户名已存在");
        }
        userService.register(username, password);
        return new AuthResponse().success();
    }

    @GetMapping("index/{username}")
    public AuthResponse index(@NotBlank(message = "{required}") @PathVariable String username) {
        // 更新登录时间
        userService.updateLoginTime(username);
        // 获取首页数据
        Map<String, Object> data = loginLogService.retrieveIndexPageData(username);
        return new AuthResponse().success().data(data);
    }
}
