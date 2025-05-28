package org.opensource.user.port.in.usecase;

import org.opensource.user.domain.User;
import org.opensource.user.port.in.command.UserCreateCommand;
import org.opensource.user.port.in.command.UserSignInCommand;

public interface UserUseCase {
    User signUp(UserCreateCommand userCreateCommand);

    String signIn(UserSignInCommand userSignInCommand);

    void checkEmailExists(String email);

    void checkNameExists(String name);

    User findUserByEmail(String email);

    User findUserById(Long id);

    void changeUserName(Long id, String name);

    void changeUserPassword(String email, String password);
}
