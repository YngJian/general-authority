package com.example.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.authority.domain.entity.RoleMenu;
import com.example.authority.domain.mapper.RoleMenuMapper;
import com.example.authority.service.IRoleMenuService;
import com.example.authority.service.IUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author MrBird
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    private final IUserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByRoleId(List<String> roleIds) {
        baseMapper.delete(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getRoleId, roleIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByMenuId(List<String> menuIds) {
        baseMapper.delete(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getMenuId, menuIds));
    }

    @Override
    public Set<Long> findUserIdByMenuIds(List<String> menuIds) {
        List<RoleMenu> roleMenus = baseMapper.selectList(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getMenuId, menuIds));
        if (CollectionUtils.isNotEmpty(roleMenus)) {
            List<String> roleIds = roleMenus.stream().map(RoleMenu::getRoleId)
                    .map(String::valueOf)
                    .collect(Collectors.toList());
            return userRoleService.findUserIdByRoleIds(roleIds);
        }
        return null;
    }

}
