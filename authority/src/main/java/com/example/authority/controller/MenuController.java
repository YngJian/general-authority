package com.example.authority.controller;


import cc.mrbird.febs.common.exception.FebsException;
import com.example.authority.common.annotation.ControllerEndpoint;
import com.example.authority.common.entity.AuthResponse;
import com.example.authority.domain.entity.Menu;
import com.example.authority.service.IMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("menu")
public class MenuController extends BaseController {

    private final IMenuService menuService;

    @GetMapping("{username}")
    public AuthResponse getUserMenus(@NotBlank(message = "{required}") @PathVariable String username) throws FebsException {
        if (!StringUtils.equalsIgnoreCase(username, getCurrentUser().getUsername())) {
            throw new FebsException("您无权获取别人的菜单");
        }
        return new AuthResponse().data(menuService.findUserMenus(username));
    }

    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取菜单树失败")
    public AuthResponse getMenuTree(Menu menu) {
        return new AuthResponse().success()
                .data(menuService.findMenus(menu).getChilds());
    }

    @PostMapping
    @RequiresPermissions("menu:add")
    @ControllerEndpoint(operation = "新增菜单/按钮", exceptionMessage = "新增菜单/按钮失败")
    public AuthResponse addMenu(@Valid Menu menu) {
        menuService.createMenu(menu);
        return new AuthResponse().success();
    }

    @GetMapping("delete/{menuIds}")
    @RequiresPermissions("menu:delete")
    @ControllerEndpoint(operation = "删除菜单/按钮", exceptionMessage = "删除菜单/按钮失败")
    public AuthResponse deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        menuService.deleteMenus(menuIds);
        return new AuthResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("menu:update")
    @ControllerEndpoint(operation = "修改菜单/按钮", exceptionMessage = "修改菜单/按钮失败")
    public AuthResponse updateMenu(@Valid Menu menu) {
        menuService.updateMenu(menu);
        return new AuthResponse().success();
    }
}
