package type.user;

import type.SuccessType;

public enum UserSuccessType implements SuccessType {
    CHECK_EMAIL_VALIDATION_SUCCESS(200, "이메일 인증에 성공하였습니다."),
    SEND_EMAIL_SUCCESS(200, "이메일을 성공적으로 전송하였습니다."),
    SIGN_UP_SUCCESS(200, "회원가입이 완료되었습니다."),
    EMAIL_CAN_USE(200, "사용 가능한 이메일입니다."),
    NAME_CAN_USE(200, "사용 가능한 이름입니다."),
    SIGN_IN_SUCCESS(200, "로그인에 성공하였습니다."),
    CHANGE_USER_NAME_SUCCESS(200, "사용자 이름을 변경하였습니다."),
    CHANGE_USER_PASSWORD_SUCCESS(200, "사용자 비밀번호를 변경하였습니다.")
    ;

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
