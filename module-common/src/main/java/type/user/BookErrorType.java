package type.user;

import type.ErrorType;

public enum BookErrorType implements ErrorType {
    GET_BOOK_LIST_ERROR(400, "책 리스트를 불러올 수 없습니다."),
    GET_BOOK_INFO_ERROR(400, "책 정보를 불러올 수 없습니다."),
    ;

    private final int code;
    private final String message;

    BookErrorType(int code, String message) {
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
