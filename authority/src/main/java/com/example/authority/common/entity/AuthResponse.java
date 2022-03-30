package com.example.authority.common.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author MrBird
 */
public class AuthResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public AuthResponse code(HttpStatus status) {
        put("code", status.value());
        return this;
    }

    public AuthResponse message(String message) {
        put("message", message);
        return this;
    }

    public AuthResponse data(Object data) {
        put("data", data);
        return this;
    }

    public AuthResponse success() {
        code(HttpStatus.OK);
        return this;
    }

    public AuthResponse fail() {
        code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public AuthResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
