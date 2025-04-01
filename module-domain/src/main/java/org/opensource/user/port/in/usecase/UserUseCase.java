package org.opensource.user.port.in.usecase;

import org.opensource.user.domain.User;
import org.opensource.user.port.in.command.UserCreateCommand;

public interface UserUseCase {
    User signUp(UserCreateCommand userCreateCommand);

    void checkEmailExists(String email);

    void checkNameExists(String name);
}
