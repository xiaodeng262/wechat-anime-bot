package com.xiaodeng.common;

import lombok.Getter;

/**
 * @author yu
 */

@Getter
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(20000, "成功"),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(20001, "未知错误"),


    /**
     * 参数错误
     */
    PARAM_ERROR(20002, "参数错误");


    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;


    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}