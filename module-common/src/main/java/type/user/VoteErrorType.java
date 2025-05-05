package type.user;

import type.ErrorType;

public enum VoteErrorType implements ErrorType {
    USER_NOT_VOTED(400, "유저가 투표를 진행하지 않았습니다."),
    VOTE_NOT_EXIST(400, "존재하지 않는 투표입니다.");

    private final int code;
    private final String message;

    VoteErrorType(int code, String message) {
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
