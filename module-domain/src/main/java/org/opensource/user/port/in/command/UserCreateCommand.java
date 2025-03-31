package org.opensource.user.port.in.command;

public record UserCreateCommand(String name, String email, String password) {
}