package xyz.luomu32.rdep.common.web;

import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import xyz.luomu32.rdep.common.ErrorResponse;

@RestControllerAdvice
@Order(10)
public class SpringMvcExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public SpringMvcExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String errorMessage = messageSource.getMessage("parameter.type.convert.fail",
                new Object[]{ex.getPropertyName(), ex.getValue(), ex.getRequiredType().getSimpleName()},
                LocaleContextHolder.getLocale());
        return ResponseEntity.status(status).headers(headers).body(new ErrorResponse(errorMessage));
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        String errorMessage = messageSource.getMessage(ex.getFieldError().getCode(), ex.getFieldError().getArguments(), ex.getFieldError().getDefaultMessage(), LocaleContextHolder.getLocale());
        return ResponseEntity.status(status).headers(headers).body(new ErrorResponse(ex.getFieldError().getDefaultMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String errorMessage = messageSource.getMessage("http.content.type.not.support",
                new Object[]{ex.getContentType().toString()},
                LocaleContextHolder.getLocale());
        return ResponseEntity.status(status).headers(headers).body(new ErrorResponse(errorMessage));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = messageSource.getMessage("parameter.missing",
                new Object[]{ex.getParameterName(), ex.getParameterType()},
                LocaleContextHolder.getLocale());
        return ResponseEntity.status(status).headers(headers).body(new ErrorResponse(errorMessage));
    }
}
