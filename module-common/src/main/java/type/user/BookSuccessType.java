package type.user;

import type.SuccessType;

public enum BookSuccessType implements SuccessType {
    GET_ALL_BOOK(200, "베스트 50권을 불러왔습니다."),
    GET_BOOK(200, "책 정보를 불러왔습니다.");

    private final int code;
    private final String message;

    BookSuccessType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
