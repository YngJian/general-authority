package com.example.authority.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author MrBird
 */
public abstract class Md5Util {

    private static final String ALGORITHM_NAME = "md5";

    private static final int HASH_ITERATIONS = 5;

    public static String encrypt(String username, String password) {
        String source = StringUtils.lowerCase(username);
        password = StringUtils.lowerCase(password);
        return password;
    }
}
