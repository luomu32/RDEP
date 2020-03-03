package xyz.luomu32.rdep.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.luomu32.rdep.common.ErrorResponse;

@Slf4j
@RestControllerAdvice
@Order(20)
public class DefaultExceptionHandler {

    private final MessageSource messageSource;

    public DefaultExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler
    public ErrorResponse serviceExceptionHandle(ServiceException e) {
        ErrorResponse response = new ErrorResponse();

        if (e.getParameters() == null || e.getParameters().length == 0) {
            response.setMessage(messageSource.getMessage(e.getMessage(), new Object[0], LocaleContextHolder.getLocale()));
        } else {
            response.setMessage(messageSource.getMessage(e.getMessage(), e.getParameters(), LocaleContextHolder.getLocale()));
        }
        if (null != e.getCode())
            response.setCode(e.getCode().toString());

        return response;
    }

    @ExceptionHandler
    public ErrorResponse defaultExceptionHandle(Throwable e) {
        log.error("", e);

        ErrorResponse response = new ErrorResponse();
        response.setMessage(messageSource.getMessage("system.error", new Object[0], "系统异常", LocaleContextHolder.getLocale()));
        return response;
    }
}
