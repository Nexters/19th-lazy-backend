package com.teamnexters.lazy.common.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    TOKEN_IS_EXPIRED(407, "C007", "Token is expired"),


    // Member
    EMAIL_DUPLICATION(410, "M001", "Email is Duplication"),
    NICKNAME_DUPLICATION(411, "M002", "NickName is Duplication"),
    LOGIN_INPUT_INVALID(412, "M003", "Login input is invalid"),
    OAUTH_PROVIDER_NOT_SUPPORT(413, "M004", "Provider is not support"),

    // Habit
    HABIT_DUPLICATION(421, "H001", "Habit is Duplication");


    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

}
