package pl.coderslab.reserveapooltable.service;

import pl.coderslab.reserveapooltable.DTO.RegisteredUserDTO;
import pl.coderslab.reserveapooltable.entity.RegisteredUser;

public interface IRegisteredUserService {
    RegisteredUser registerNewUserAccount(RegisteredUserDTO registeredUserDTO);
}
