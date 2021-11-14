package com.teamnexters.lazy.common.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "C001", "잘못된 요청 값입니다."),
    INVALID_TYPE_VALUE(400, "C002", "잘못된 타입 값입니다."),
    HANDLE_UNAUTHORIZED_JWT(401, "C003", "인증되지 않은 사용자입니다."),
    HANDLE_ACCESS_DENIED(403, "C004", "권한이 없는 사용자입니다."),
    ENTITY_NOT_FOUND(404, "C005", "Entity 를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(405, "C006", "허용되지 않은 HTTP 메소드입니다."),
    TOKEN_IS_EXPIRED(407, "C007", "토큰이 만료되었습니다."),
    TOKEN_IS_NOT_VALID(408, "C008", "토큰이 유효하지 않습니다."),

    INTERNAL_SERVER_ERROR(500, "C008", "서버 내부 에러입니다."),
    EXTERNAL_SERVER_ERROR(501, "C009", "외부 API 통신 에러입니다."),

    // Member
    EMAIL_DUPLICATION(410, "M001", "이미 등록된 이메일입니다."),
    NICKNAME_DUPLICATION(411, "M002", "이미 등록된 닉네임입니다."),
    LOGIN_INPUT_INVALID(412, "M003", "잘못된 로그인 요청 값입니다."),
    OAUTH_PROVIDER_NOT_SUPPORT(413, "M004", "제공되지 않는 소셜로그인입니다."),
    MEMBER_NOT_FOUND(414, "M005", "존재하지 않는 회원입니다."),

    // Habit
    HABIT_DUPLICATION(421, "H001", "이미 등록된 습관입니다.");


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
