package com.xiaodeng.common;

import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;

/**
 * 统一结果返回
 *
 * @author yu
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -3373464376588025797L;
    /**
     * 状态CODE
     */
    private int code;

    /**
     * Http请求头
     */
    private HttpHeaders httpHeaders;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回对象
     */
    private T data;


    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public Result(int code, String msg) {
        this.code = code;
        this.message = msg;
    }


    public Result(T data) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = ResultCodeEnum.SUCCESS.getMessage();
        this.data = data;
    }

    public Result(HttpHeaders httpHeaders, T data) {
        this.httpHeaders = httpHeaders;
        this.data = data;
    }


    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg);
    }




    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

}
