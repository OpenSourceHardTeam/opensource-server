package org.opensource.user.mapper;

import org.opensource.user.port.in.command.UserCreateCommand;
import org.opensource.user.port.in.command.UserSignInCommand;

public class UserMapper {
    public static UserCreateCommand toUserCreateCommand(
            String name, String email, String password
    ) {
        return new UserCreateCommand(name, email, password);
    }

    public static UserSignInCommand toUserSignInCommand(
            String email, String password
    ) {
        return new UserSignInCommand(email, password);
    }
}
