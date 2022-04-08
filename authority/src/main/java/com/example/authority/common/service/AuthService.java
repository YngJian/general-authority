package com.example.authority.common.service;

import com.example.authority.common.entity.JwtUser;
import com.example.authority.domain.entity.User;
import com.example.authority.service.IUserService;
import com.example.authority.utils.CurrentUserUtils;
import com.vayne.security.constants.SecurityConstants;
import com.vayne.security.entity.LoginRequest;
import com.vayne.security.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author shuang.kou
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {
    private final IUserService userService;
    private final StringRedisTemplate stringRedisTemplate;
    private final CurrentUserUtils currentUserUtils;

    public String createToken(LoginRequest loginRequest) {
        User user = userService.findByName(loginRequest.getUsername());
        if (!userService.check(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("The user name or password is not correct.");
        }
        userService.doGetUserAuthorizationInfo(user);
        JwtUser jwtUser = new JwtUser(user);

        List<String> authorities = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = JwtTokenUtils.createToken(user.getUsername(), String.valueOf(user.getUserId()),
                authorities, loginRequest.getRememberMe());

        long expiration = loginRequest.getRememberMe() ? SecurityConstants.EXPIRATION_REMEMBER : SecurityConstants.EXPIRATION;
        stringRedisTemplate.opsForValue().set(SecurityConstants.TOKEN_REDIS_PREFIX + user.getUserId().toString(),
                token, expiration, TimeUnit.SECONDS);
        return token;
    }

    public void removeToken() {
        stringRedisTemplate.delete(SecurityConstants.TOKEN_REDIS_PREFIX + currentUserUtils.getCurrentUser().getUserId().toString());
    }
}
