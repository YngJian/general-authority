package com.example.authority.controller;

import cc.mrbird.febs.common.entity.Strings;
import cc.mrbird.febs.common.utils.Md5Util;
import com.example.authority.common.annotation.ControllerEndpoint;
import com.example.authority.common.entity.AuthResponse;
import com.example.authority.common.entity.QueryRequest;
import com.example.authority.domain.entity.User;
import com.example.authority.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController extends BaseController {

    private final IUserService userService;

    @GetMapping("{username}")
    public User getUser(@NotBlank(message = "{required}") @PathVariable String username) {
        return userService.findUserDetailList(username);
    }

    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username, String userId) {
        return userService.findByName(username) == null || StringUtils.isNotBlank(userId);
    }

    @GetMapping("list")
    @RequiresPermissions("user:view")
    public AuthResponse userList(User user, QueryRequest request) {
        return new AuthResponse().success()
                .data(getDataTable(userService.findUserDetailList(user, request)));
    }

    @PostMapping
    @RequiresPermissions("user:add")
    @ControllerEndpoint(operation = "新增用户", exceptionMessage = "新增用户失败")
    public AuthResponse addUser(@Valid User user) {
        userService.createUser(user);
        return new AuthResponse().success();
    }

    @GetMapping("delete/{userIds}")
    @RequiresPermissions("user:delete")
    @ControllerEndpoint(operation = "删除用户", exceptionMessage = "删除用户失败")
    public AuthResponse deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) {
        userService.deleteUsers(StringUtils.split(userIds, Strings.COMMA));
        return new AuthResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("user:update")
    @ControllerEndpoint(operation = "修改用户", exceptionMessage = "修改用户失败")
    public AuthResponse updateUser(@Valid User user) {
        if (user.getUserId() == null) {
            throw new AuthResponse("用户ID为空");
        }
        userService.updateUser(user);
        return new AuthResponse().success();
    }

    @PostMapping("password/reset/{usernames}")
    @RequiresPermissions("user:password:reset")
    @ControllerEndpoint(exceptionMessage = "重置用户密码失败")
    public AuthResponse resetPassword(@NotBlank(message = "{required}") @PathVariable String usernames) {
        userService.resetPassword(StringUtils.split(usernames, Strings.COMMA));
        return new AuthResponse().success();
    }

    @PostMapping("password/update")
    @ControllerEndpoint(exceptionMessage = "修改密码失败")
    public AuthResponse updatePassword(
            @NotBlank(message = "{required}") String oldPassword,
            @NotBlank(message = "{required}") String newPassword) {
        User user = getCurrentUser();
        if (!StringUtils.equals(user.getPassword(), Md5Util.encrypt(user.getUsername(), oldPassword))) {
            throw new RuntimeException("原密码不正确");
        }
        userService.updatePassword(user.getUsername(), newPassword);
        return new AuthResponse().success();
    }

    @GetMapping("avatar/{image}")
    @ControllerEndpoint(exceptionMessage = "修改头像失败")
    public AuthResponse updateAvatar(@NotBlank(message = "{required}") @PathVariable String image) {
        userService.updateAvatar(getCurrentUser().getUsername(), image);
        return new AuthResponse().success();
    }

    @PostMapping("theme/update")
    @ControllerEndpoint(exceptionMessage = "修改系统配置失败")
    public AuthResponse updateTheme(String theme, String isTab) {
        userService.updateTheme(getCurrentUser().getUsername(), theme, isTab);
        return new AuthResponse().success();
    }

    @PostMapping("profile/update")
    @ControllerEndpoint(exceptionMessage = "修改个人信息失败")
    public AuthResponse updateProfile(User user) throws RuntimeException {
        user.setUserId(getCurrentUser().getUserId());
        userService.updateProfile(user);
        return new AuthResponse().success();
    }
}
