package com.example.authority.controller;

import com.example.authority.common.annotation.Limit;
import com.example.authority.common.entity.AuthResponse;
import com.example.authority.common.properties.AuthProperties;
import com.example.authority.domain.entity.User;
import com.example.authority.service.ILoginLogService;
import com.example.authority.service.IUserService;
import com.example.authority.utils.JWTUtil;
import com.example.authority.utils.Md5Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
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
            @NotBlank(message = "{required}") String password) throws RuntimeException {
        User user = userService.findByName(username);
        String encrypt = Md5Util.encrypt(username, password);
        if (!user.getPassword().equals(encrypt)) {
            log.error("用户名密码不匹配！");
            return new AuthResponse().fail("用户名密码不匹配！");
        }
        // 保存登录日志
        loginLogService.saveLoginLog(username);
        return new AuthResponse().success().data(JWTUtil.sign(username, String.valueOf(user.getUserId())));
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

    @RequestMapping(path = "/unauthorized/{message}")
    public AuthResponse unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return new AuthResponse().success(HttpStatus.FORBIDDEN, message);
    }
}
