package org.opensource.user.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LoginType {
    NORMAL("일반"),
    KAKAO("카카오"),
    ;

    private final String type;
}
