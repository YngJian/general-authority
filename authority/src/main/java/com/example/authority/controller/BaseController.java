package com.example.authority.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.authority.domain.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MrBird
 */
public class BaseController {

    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    protected User getCurrentUser() {
        return (User) getSubject().getPrincipal();
    }

    protected Session getSession() {
        return getSubject().getSession();
    }

    protected Session getSession(Boolean flag) {
        return getSubject().getSession(flag);
    }

    protected Map<String, Object> getDataTable(IPage<?> pageInfo) {
        return getDataTable(pageInfo.getRecords(), pageInfo.getTotal());
    }

    protected Map<String, Object> getDataTable(Object rows, Object total) {
        Map<String, Object> data = new HashMap<>(4);
        data.put("rows", rows);
        data.put("total", total);
        return data;
    }

}
