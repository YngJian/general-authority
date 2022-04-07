package com.example.authority.controller;

import com.example.authority.common.annotation.Limit;
import com.example.authority.common.entity.AuthResponse;
import com.example.authority.common.service.AuthService;
import com.example.authority.domain.entity.User;
import com.example.authority.service.ILoginLogService;
import com.example.authority.service.IUserService;
import com.vayne.security.entity.LoginRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final AuthService authService;
    private final ILoginLogService loginLogService;

    @PostMapping("login")
    @Limit(key = "login", period = 60, count = 10, name = "登录接口", prefix = "limit")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) throws RuntimeException {
        String token = authService.createToken(loginRequest);
        // 保存登录日志
        loginLogService.saveLoginLog(loginRequest.getUsername());
        return new AuthResponse().success().data(token);
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

    @PostMapping("/logout")
    @ApiOperation("退出")
    public ResponseEntity<Void> logout() {
        authService.removeToken();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
