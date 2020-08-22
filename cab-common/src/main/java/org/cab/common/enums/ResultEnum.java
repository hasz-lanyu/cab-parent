package org.cab.common.enums;

public enum ResultEnum {
    OK(200, "操作成功"),
    ERROR(500, "操作异常"),
    PARAM_ERR(404, "参数异常"),
    UNAUTHORIZED(401, "登录失败"),
    FORBIDDEN(403, "权限不足"),
    UNKNOWN(1001, "未知异常");


    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
