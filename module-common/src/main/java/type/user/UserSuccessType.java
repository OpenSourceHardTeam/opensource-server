package type.user;

import type.SuccessType;

public enum UserSuccessType implements SuccessType {
    CHECK_EMAIL_VALIDATION_SUCCESS(200, "이메일 인증에 성공하였습니다."),
    SEND_EMAIL_SUCCESS(200, "이메일을 성공적으로 전송하였습니다.");

    private final int code;
    private final String message;

    UserSuccessType(int code, String message) {
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
