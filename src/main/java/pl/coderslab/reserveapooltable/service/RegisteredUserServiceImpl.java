package pl.coderslab.reserveapooltable.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.reserveapooltable.DTO.RegisteredUserDTO;
import pl.coderslab.reserveapooltable.entity.RegisteredUser;
import pl.coderslab.reserveapooltable.entity.Role;
import pl.coderslab.reserveapooltable.errors.UserAlreadyExistException;
import pl.coderslab.reserveapooltable.repository.RegisteredUserRepository;
import pl.coderslab.reserveapooltable.repository.RoleRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class RegisteredUserServiceImpl implements RegisteredUserService, RegisteredUserDTOService {
    private final RegisteredUserRepository registeredUserRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisteredUserServiceImpl(RegisteredUserRepository registeredUserRepository, RoleRepository roleRepository,
                                     BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.registeredUserRepository = registeredUserRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public RegisteredUser findRegisteredUserByUsername(String username) {
        return registeredUserRepository.findRegisteredUserByUsername(username);
    }

    @Override
    public void saveRegisteredUser(RegisteredUser registeredUser) {
        if (emailExist(registeredUser.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + registeredUser.getEmail());
        }
        if (usernameExist(registeredUser.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that username address: "
                    + registeredUser.getUsername());
        }
        registeredUser.setPassword(passwordEncoder.encode(registeredUser.getPassword()));
        registeredUser.setUsername(registeredUser.getUsername());
        registeredUser.setEnabled(1);
        Optional<Set<Role>> roleOptional = Optional.ofNullable(registeredUser.getRoles());
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (roleOptional.isEmpty()) {
            registeredUser.setRoles(new HashSet<>(Arrays.asList(userRole)));
        } else {
            Set<Role> roleSet = registeredUser.getRoles();
            roleSet.add(userRole);
            registeredUser.setRoles(roleSet);
        }
        registeredUser.setEmail(registeredUser.getEmail());
        registeredUser.setPhoneNumber(registeredUser.getPhoneNumber());
        registeredUser.setUsageAcceptance(registeredUser.isUsageAcceptance());
        registeredUserRepository.save(registeredUser);
    }

    public RegisteredUser registerNewUserAccount(RegisteredUserDTO registeredUserDTO) throws UserAlreadyExistException {
        if (emailExist(registeredUserDTO.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + registeredUserDTO.getEmail());
        }
        if (usernameExist(registeredUserDTO.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that username address: "
                    + registeredUserDTO.getUsername());
        }
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setUsername(registeredUserDTO.getUsername());
        registeredUser.setPassword(passwordEncoder.encode(registeredUserDTO.getPassword()));
        registeredUser.setEmail(registeredUserDTO.getEmail());
        registeredUser.setPhoneNumber(registeredUserDTO.getPhoneNumber());
        registeredUser.setUsageAcceptance(registeredUserDTO.isUsageAcceptance());
        registeredUser.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_USER");
        registeredUser.setRoles(new HashSet<>(Arrays.asList(userRole)));
        return registeredUserRepository.save(registeredUser);

    }

    private boolean emailExist(String email) {
        return registeredUserRepository.findUserByEmail(email) != null;
    }

    private boolean usernameExist(String username) {
        return registeredUserRepository.findRegisteredUserByUsername(username) != null;
    }

}