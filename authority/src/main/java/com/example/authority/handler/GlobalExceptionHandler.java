package com.example.authority.handler;

import com.example.authority.common.entity.AuthResponse;
import com.example.authority.common.entity.Strings;
import com.example.authority.common.exception.LimitAccessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

/**
 * @author MrBird
 */
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public AuthResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return new AuthResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message("系统内部异常");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public AuthResponse handleException(RuntimeException e) {
        log.debug("系统错误", e);
        return new AuthResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
    }

    /**
     * 统一处理请求参数校验(实体对象传参-form)
     *
     * @param e BindException
     * @return AuthResponse
     */
    @ExceptionHandler(BindException.class)
    public AuthResponse validExceptionHandler(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(Strings.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new AuthResponse().code(HttpStatus.BAD_REQUEST).message(message.toString());
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return AuthResponse
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public AuthResponse handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), Strings.DOT);
            message.append(pathArr[1]).append(violation.getMessage()).append(Strings.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new AuthResponse().code(HttpStatus.BAD_REQUEST).message(message.toString());
    }

    /**
     * 统一处理请求参数校验(json)
     *
     * @param e ConstraintViolationException
     * @return AuthResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AuthResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(Strings.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        log.error(message.toString(), e);
        return new AuthResponse().code(HttpStatus.BAD_REQUEST).message(message.toString());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public AuthResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String message = String.format("该方法不支持%s请求", StringUtils.substringBetween(e.getMessage(), Strings.SINGLE_QUOTE, Strings.SINGLE_QUOTE));
        log.error(message, e.getMessage());
        return new AuthResponse().code(HttpStatus.METHOD_NOT_ALLOWED).message(message);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public AuthResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        String message = "文件大小超出限制";
        log.error(message, e.getMessage());
        return new AuthResponse().code(HttpStatus.PAYLOAD_TOO_LARGE).message(message);
    }

    @ExceptionHandler(value = LimitAccessException.class)
    public AuthResponse handleLimitAccessException(LimitAccessException e) {
        log.error("LimitAccessException", e);
        return new AuthResponse().code(HttpStatus.TOO_MANY_REQUESTS).message(e.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public AuthResponse handleUnauthorizedException(UnauthorizedException e) {
        log.error("UnauthorizedException, {}", e.getMessage());
        return new AuthResponse().code(HttpStatus.FORBIDDEN).message(e.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public AuthResponse handleAuthenticationException(AuthenticationException e) {
        log.error("AuthenticationException, {}", e.getMessage());
        return new AuthResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public AuthResponse handleAuthorizationException(AuthorizationException e) {
        log.error("AuthorizationException, {}", e.getMessage());
        return new AuthResponse().code(HttpStatus.UNAUTHORIZED).message(e.getMessage());
    }

    @ExceptionHandler(value = ExpiredSessionException.class)
    public AuthResponse handleExpiredSessionException(ExpiredSessionException e) {
        log.error("ExpiredSessionException", e);
        return new AuthResponse().code(HttpStatus.UNAUTHORIZED).message(e.getMessage());
    }
}
