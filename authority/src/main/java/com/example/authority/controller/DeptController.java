package com.example.authority.controller;


import com.example.authority.common.annotation.ControllerEndpoint;
import com.example.authority.common.entity.AuthResponse;
import com.example.authority.common.entity.Strings;
import com.example.authority.domain.entity.Dept;
import com.example.authority.service.IDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("dept")
public class DeptController {

    private final IDeptService deptService;

    @GetMapping("select/tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public AuthResponse getDeptTree() throws RuntimeException {
        return new AuthResponse().success().data(deptService.findDept());
    }

    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public AuthResponse getDeptTree(Dept dept) throws RuntimeException {
        return new AuthResponse().success().data(deptService.findDept(dept));
    }

    @PostMapping
    @ControllerEndpoint(operation = "新增部门", exceptionMessage = "新增部门失败")
    @PreAuthorize("hasRole('dept:add')")
    public AuthResponse addDept(@Valid Dept dept) {
        deptService.createDept(dept);
        return new AuthResponse().success();
    }

    @GetMapping("delete/{deptIds}")
    @ControllerEndpoint(operation = "删除部门", exceptionMessage = "删除部门失败")
    @PreAuthorize("hasRole('dept:delete')")
    public AuthResponse deleteDept(@NotBlank(message = "{required}") @PathVariable String deptIds) throws RuntimeException {
        deptService.deleteDept(StringUtils.split(deptIds, Strings.COMMA));
        return new AuthResponse().success();
    }

    @PostMapping("update")
    @ControllerEndpoint(operation = "修改部门", exceptionMessage = "修改部门失败")
    @PreAuthorize("hasRole('dept:update')")
    public AuthResponse updateDept(@Valid Dept dept) throws RuntimeException {
        deptService.updateDept(dept);
        return new AuthResponse().success();
    }
}
