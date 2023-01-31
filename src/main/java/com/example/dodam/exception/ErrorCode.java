package com.example.dodam.exception;

public enum ErrorCode {

    USER_NOT_FOUND(400, "해당 유저를 찾을 수 없습니다."),

    DUPLICATED_EMAIL(400, "이미 존재하는 E-mail입니다."),
    DUPLICATED_NICKNAME(400, "이미 존재하는 Ninkname입니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
