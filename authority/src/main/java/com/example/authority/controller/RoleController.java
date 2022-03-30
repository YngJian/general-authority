package com.example.authority.controller;


import com.example.authority.common.annotation.ControllerEndpoint;
import com.example.authority.common.entity.AuthResponse;
import com.example.authority.common.entity.QueryRequest;
import com.example.authority.domain.entity.Role;
import com.example.authority.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("role")
public class RoleController extends BaseController {

    private final IRoleService roleService;

    @GetMapping
    public AuthResponse getAllRoles(Role role) {
        return new AuthResponse().success().data(roleService.findRoles(role));
    }

    @GetMapping("list")
    @RequiresPermissions("role:view")
    public AuthResponse roleList(Role role, QueryRequest request) {
        return new AuthResponse().success()
                .data(getDataTable(roleService.findRoles(role, request)));
    }

    @PostMapping
    @RequiresPermissions("role:add")
    @ControllerEndpoint(operation = "新增角色", exceptionMessage = "新增角色失败")
    public AuthResponse addRole(@Valid Role role) {
        roleService.createRole(role);
        return new AuthResponse().success();
    }

    @GetMapping("delete/{roleIds}")
    @RequiresPermissions("role:delete")
    @ControllerEndpoint(operation = "删除角色", exceptionMessage = "删除角色失败")
    public AuthResponse deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        roleService.deleteRoles(roleIds);
        return new AuthResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("role:update")
    @ControllerEndpoint(operation = "修改角色", exceptionMessage = "修改角色失败")
    public AuthResponse updateRole(Role role) {
        roleService.updateRole(role);
        return new AuthResponse().success();
    }
}
