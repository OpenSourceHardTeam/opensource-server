package org.opensource.user.mapper;

import org.opensource.user.port.in.command.UserCreateCommand;

public class UserMapper {
    public static UserCreateCommand toUserCreateCommand(
            String name, String email, String password
    ) {
        return new UserCreateCommand(name, email, password);
    }
}
