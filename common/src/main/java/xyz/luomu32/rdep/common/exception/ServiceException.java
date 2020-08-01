package xyz.luomu32.rdep.common.exception;

import lombok.Getter;

public class ServiceException extends RuntimeException {

    @Getter
    private Integer code;

    @Getter
    private String detail;

    @Getter
    private Object[] parameters;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public ServiceException(String message, Object[] parameters) {
        super(message);
        this.parameters = parameters;
    }

    public ServiceException(String message, Object[] parameters, String detail) {
        super(message);
        this.parameters = parameters;
        this.detail = detail;
    }
}
