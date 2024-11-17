package com.achobeta.types.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 5317680961212299217L;

    /** 异常码 */
    private Integer code;

    /** 异常信息 */
    private String info;

    public AppException(Integer code) {
        this.code = code;
    }

    public AppException(Integer code, Throwable cause) {
        this.code = code;
        super.initCause(cause);
    }

    public AppException(Integer code, String message) {
        this.code = code;
        this.info = message;
    }

    public AppException(Integer code, String message, Throwable cause) {
        this.code = code;
        this.info = message;
        super.initCause(cause);
    }

    @Override
    public String toString() {
        return "com.achobeta.x.api.types.exception.XApiException{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

}
