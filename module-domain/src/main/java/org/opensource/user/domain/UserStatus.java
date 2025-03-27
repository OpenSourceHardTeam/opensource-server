package org.opensource.user.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("활성화"),
    INACTIVE("비활성화"),
    SUSPENDED("중단"),
    ;

    private final String status;
}
