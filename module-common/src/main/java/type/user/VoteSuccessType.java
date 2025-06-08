package type.user;

import type.SuccessType;

public enum VoteSuccessType implements SuccessType {
    VOTE_SUCCESS(200, "투표를 완료하였습니다."),
    ADD_VOTE_SUCCESS(200, "투표를 생성하였습니다."),
    CHANGE_VOTE_ANSWERED_SUCCESS(200, "투표를 변경하였습니다."),
    GET_VOTE_ANSWERED_SUCCESS(200, "유저의 투표 내용입니다."),
    GET_ALL_BOOK_VOTE_SUCCESS(200, "책의 모든 투표 리스트를 불러왔습니다."),
    GET_ALL_VOTE_SUCCESS(200, "모든 투표 리스트를 불러왔습니다."),
    DELETE_VOTE_SUCCESS(200, "투표를 삭제하였습니다.");

    private final int code;
    private final String message;

    VoteSuccessType(int code, String message) {
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
