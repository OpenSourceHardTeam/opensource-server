package type.user;

import type.ErrorType;

public enum UserErrorType implements ErrorType {
    CHECK_EMAIL_VALIDATION_ERROR(401, "인증번호가 일치하지 않습니다."),
    SEND_EMAIL_ERROR(500, "이메일 전송에 실패하였습니다. 다시 시도해주세요."),
    EMAIL_AUTH_TIMEOUT_ERROR(400, "인증 시간을 초과하였습니다. 다시 시도해주세요"),
    EMAIL_ALREADY_EXISTS(400, "이미 등록된 이메일입니다."),
    NAME_ALREADY_EXISTS(400, "이미 존재하는 이름입니다."),
    SIGN_UP_ERROR(500, "회원가입에 실패하였습니다."),
    EMAIL_NOT_EXIST(400, "등록된 이메일이 없습니다."),
    TOKEN_TIME_EXPIRED_ERROR(401, "토큰 기간이 만료되었습니다."),
    USER_PASSWORD_NOT_MATCH(401, "비밀번호를 잘못 입력하였습니다."),
    USER_NOT_EXIST(400, "유저가 존재하지 않습니다."),
    TOKEN_NOT_FOUND(401, "accessToken을 찾을 수 없습니다."),
    TOKEN_MALFORMED(401, "잘못된 토큰 형식입니다."),
    TOKEN_INVALID_FORMAT(401, "토큰 포맷이 올바르지 않습니다."),
    TOKEN_SIGNATURE_INVALID(401, "토큰 서명이 올바르지 않습니다."),
    ;

    private final int code;
    private final String message;

    UserErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
