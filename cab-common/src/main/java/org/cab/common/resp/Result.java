package org.cab.common.resp;

import org.cab.common.enums.ResultEnum;

import java.io.Serializable;


/**
 * 统一返回结果集类
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 5784873149209683708L;
    private Object data;
    private boolean success;
    private Integer code;
    private String message;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private int total;

    private Result() {
    }

    public static Result ok(Object data, ResultEnum resultEnum) {
        Result r = ok(resultEnum);
        r.setData(data);
        return r;
    }

    public static Result ok(ResultEnum resultEnum) {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(resultEnum.getCode());
        r.setMessage(resultEnum.getMessage());
        return r;
    }

    public static Result ok() {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(ResultEnum.OK.getCode());
        r.setMessage(ResultEnum.OK.getMessage());
        return r;
    }

    public static Result ok(Object data) {
        Result r = ok();
        r.setData(data);
        return r;
    }
    public static Result ok(Object data,int total) {
        Result r = ok();
        r.setTotal(total);
        r.setData(data);
        return r;
    }

    public static Result ok(Integer code) {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(code);
        return r;
    }

    public static Result ok(String message) {
        Result r = ok();
        r.setMessage(message);
        return r;
    }

    public static Result ok(Integer code, String message) {
        Result r = ok(message);
        r.setCode(code);
        return r;
    }

    public static Result ok(Integer code,String message,Object data){
        Result r = ok(code, message);
        r.setData(data);
        return r ;
    }

    public static Result error(ResultEnum resultEnum) {
        Result r = new Result();
        r.setCode(resultEnum.getCode());
        r.setMessage(resultEnum.getMessage());
        return r;
    }

    public static Result error(String message) {
        Result r = error();
        r.setMessage(message);
        return r;
    }

    public static Result error(Integer code, String message) {
        Result r = error(message);
        r.setCode(code);
        return r;
    }

    public static Result error(Object data, ResultEnum resultEnum) {
        Result r = error(resultEnum);
        r.setData(data);
        return r;
    }

    public static Result error() {
        Result r = new Result();
        r.setCode(ResultEnum.ERROR.getCode());
        r.setMessage(ResultEnum.ERROR.getMessage());
        return r;
    }

    public static Result error(Integer code) {
        Result r = new Result();
        r.setCode(code);
        return r;
    }

    public static Result validateErr() {
        Result r = new Result();
        r.setCode(ResultEnum.PARAM_ERR.getCode());
        r.setMessage(ResultEnum.PARAM_ERR.getMessage());
        return r;
    }

    public static Result unAuthorized() {
        Result r = new Result();
        r.setCode(ResultEnum.UNAUTHORIZED.getCode());
        r.setMessage(ResultEnum.UNAUTHORIZED.getMessage());
        return r;
    }

    public static Result unAuthorized(Object data) {
        Result r = unAuthorized();
        r.setData(data);
        return r;
    }

    public static Result forbidden() {
        Result r = new Result();
        r.setCode(ResultEnum.FORBIDDEN.getCode());
        r.setMessage(ResultEnum.FORBIDDEN.getMessage());
        return r;
    }

    public static Result forbidden(Object data) {
        Result r = forbidden();
        r.setData(data);
        return r;
    }

    public static Result customResult(Object data, boolean success, ResultEnum resultEnum) {
        Result r = new Result();
        r.setSuccess(success);
        r.setData(data);
        r.setCode(resultEnum.getCode());
        r.setMessage(resultEnum.getMessage());
        return r;

    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
