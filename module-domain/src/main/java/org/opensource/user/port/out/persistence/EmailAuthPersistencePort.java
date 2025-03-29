package org.opensource.user.port.out.persistence;

import org.opensource.user.port.in.command.EmailAuthCommand;

public interface EmailAuthPersistencePort {
    void setData(EmailAuthCommand emailAuthCommand);

    boolean existData(String email);

    String getData(String email);

    void deleteData(String email);
}
