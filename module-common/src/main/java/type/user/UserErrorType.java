package type.user;

import type.ErrorType;

public enum UserErrorType implements ErrorType {
    CHECK_EMAIL_VALIDATION_ERROR(401, "인증번호가 일치하지 않습니다."),
    SEND_EMAIL_ERROR(500, "이메일 전송에 실패하였습니다. 다시 시도해주세요."),
    EMAIL_AUTH_TIMEOUT_ERROR(400, "인증 시간을 초과하였습니다. 다시 시도해주세요")
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
