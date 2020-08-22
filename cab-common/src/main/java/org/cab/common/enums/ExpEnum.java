package org.cab.common.enums;

public enum ExpEnum {
    SELECT_ERROR("数据查询异常",500);

    ExpEnum(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private String message;
    private Integer code;
}
