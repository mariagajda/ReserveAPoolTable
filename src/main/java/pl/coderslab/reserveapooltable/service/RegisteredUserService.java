package pl.coderslab.reserveapooltable.service;

import pl.coderslab.reserveapooltable.DTO.RegisteredUserDTO;
import pl.coderslab.reserveapooltable.entity.RegisteredUser;
import pl.coderslab.reserveapooltable.entity.User;

public interface RegisteredUserService {
    RegisteredUser findRegisteredUserByUsername(String username);
//    void saveRegisteredUser(RegisteredUser registeredUser);
}
