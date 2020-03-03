package xyz.luomu32.rdep.common.exception;

import lombok.Getter;

public class ServiceException extends RuntimeException {

    @Getter
    private Integer code;

    @Getter
    private Object[] parameters;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Object[] parameters) {
        super(message);
        this.parameters = parameters;
    }
}
