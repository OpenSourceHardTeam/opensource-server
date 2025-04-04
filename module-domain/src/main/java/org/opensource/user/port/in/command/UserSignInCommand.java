package org.opensource.user.port.in.command;

public record UserSignInCommand(String email, String password) {
}
