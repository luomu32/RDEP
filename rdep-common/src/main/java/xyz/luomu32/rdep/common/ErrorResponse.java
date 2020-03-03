package xyz.luomu32.rdep.common;

import lombok.Data;

@Data
public class ErrorResponse {

    private String code;

    private String message;

    public ErrorResponse() {

    }

    public ErrorResponse(String message) {
        this.message = message;
    }
}
