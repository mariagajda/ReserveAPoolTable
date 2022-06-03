package pl.coderslab.reserveapooltable.service;

import pl.coderslab.reserveapooltable.entity.RegisteredUser;

public interface RegisteredUserService {
    RegisteredUser findRegisteredUserByUsername(String username);

    void saveRegisteredUser(RegisteredUser registeredUser);
}
