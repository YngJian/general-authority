package com.example.authority.common.exception;

/**
 * 限流异常
 *
 * @author MrBird
 */
public class LimitAccessException extends RuntimeException {

    private static final long serialVersionUID = -3608667856397125671L;

    public LimitAccessException(String message) {
        super(message);
    }
}