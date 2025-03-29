package org.opensource.user.port.in.command;

import lombok.Builder;

@Builder
public record EmailAuthCommand(String email, String authCode, long duration) {
}
