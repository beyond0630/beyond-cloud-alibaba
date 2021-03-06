package com.beyond.cloud.alibaba.exception;

/**
 * @author lucifer
 * @date 2020/7/31 15:53
 */
public class UnauthorizedException extends RuntimeException {

    private int code;

    public static final UnauthorizedException instance = new UnauthorizedException(401);

    public UnauthorizedException(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }
}
