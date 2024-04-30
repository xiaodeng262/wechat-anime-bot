package com.xiaodeng.common;

/**
 * 自定义错误码
 *
 * @author xiaodneg
 */
public enum ErrorCode {

    SUCCESS(0, "ok");
    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
