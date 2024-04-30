package com.xiaodeng.exception;


import com.xiaodeng.common.ErrorCode;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author yu
 */
@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 3949392716266803898L;
    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
        this.code = -1;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

}
