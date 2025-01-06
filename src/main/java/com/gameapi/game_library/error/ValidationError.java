package com.gameapi.game_library.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class ValidationError {

    @Autowired
    MessageSource messageSource;


    private String code;
    private String message;

    public ValidationError() {}

    public ValidationError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
