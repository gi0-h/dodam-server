package com.example.dodam.common.exception;

public class RegisterException extends RuntimeException{
    private final ErrorCode errorCode;

    public RegisterException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
