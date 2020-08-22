package org.cab.common.exception;

import org.cab.common.enums.ExpEnum;

public class CustomException extends  RuntimeException{
    private Integer code;

    public CustomException(String message){
        super(message);
    }

    public CustomException(String message,Integer code){
        super(message);
        this.code = code;
    }
    public CustomException(ExpEnum expEnum){
        super(expEnum.getMessage());
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
