package pl.coderslab.reserveapooltable.DTO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.coderslab.reserveapooltable.entity.RegisteredUser;

@Component
public class RegisteredUserDTOMapper {
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisteredUserDTOMapper(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public RegisteredUser toRegisteredUser(RegisteredUserDTO registeredUserDTO){
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setUsername(registeredUserDTO.getUsername());
        registeredUser.setPassword(passwordEncoder.encode(registeredUserDTO.getPassword()));
        registeredUser.setEmail(registeredUserDTO.getEmail());
        registeredUser.setPhoneNumber(registeredUserDTO.getPhoneNumber());
        registeredUser.setUsageAcceptance(registeredUserDTO.isUsageAcceptance());
        return registeredUser;
    }
}
