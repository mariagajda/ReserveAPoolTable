package pl.coderslab.reserveapooltable.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.reserveapooltable.DTO.RegisteredUserDTO;
import pl.coderslab.reserveapooltable.DTO.RegisteredUserDTOMapper;
import pl.coderslab.reserveapooltable.entity.RegisteredUser;
import pl.coderslab.reserveapooltable.entity.Role;
import pl.coderslab.reserveapooltable.errors.UserAlreadyExistException;
import pl.coderslab.reserveapooltable.repository.RegisteredUserRepository;
import pl.coderslab.reserveapooltable.repository.RoleRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class RegisteredUserServiceImplTest {


    RegisteredUserRepository registeredUserRepositoryMock;
    RoleRepository roleRepositoryMock;
    BCryptPasswordEncoder passwordEncoderMock;
    RegisteredUserDTOMapper registeredUserDTOMapperMock;

    RegisteredUserServiceImpl registeredUserServiceImpl;


    @BeforeEach
    public void setUp() {
        this.registeredUserRepositoryMock = mock(RegisteredUserRepository.class);
        this.roleRepositoryMock = mock(RoleRepository.class);
        this.passwordEncoderMock = mock(BCryptPasswordEncoder.class);
        this.registeredUserDTOMapperMock = mock(RegisteredUserDTOMapper.class);

        registeredUserServiceImpl = new RegisteredUserServiceImpl(registeredUserRepositoryMock, roleRepositoryMock, passwordEncoderMock, registeredUserDTOMapperMock);
    }


    @Test
    void should_ThrowException_WhenEmailExist() {
        //given
        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO();
        registeredUserDTO.setEmail("email@email.com");
        registeredUserDTO.setUsername("newUser");
        registeredUserDTO.setPassword("Password1.");
        registeredUserDTO.setPhoneNumber("123123123");
        registeredUserDTO.setUsageAcceptance(true);
        when(this.registeredUserRepositoryMock
                .findUserByEmail(registeredUserDTO.getEmail()))
                .thenReturn(new RegisteredUser());
        //when
        Executable executable = () -> registeredUserServiceImpl
                .registerNewUserAccount(registeredUserDTO);

        //then
        assertThrows(UserAlreadyExistException.class, executable);
    }

    @Test
    void should_ThrowException_WhenUsernameExist() {
        //given
        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO();
        registeredUserDTO.setEmail("email@email.com");
        registeredUserDTO.setUsername("newUser");
        registeredUserDTO.setPassword("Password1.");
        registeredUserDTO.setPhoneNumber("123123123");
        registeredUserDTO.setUsageAcceptance(true);
        when(this.registeredUserRepositoryMock.findUserByEmail(registeredUserDTO.getEmail()))
                .thenReturn(null);
        when(this.registeredUserRepositoryMock
                .findRegisteredUserByUsername(registeredUserDTO.getUsername()))
                .thenReturn(new RegisteredUser());

        //when
        Executable executable = () -> registeredUserServiceImpl
                .registerNewUserAccount(registeredUserDTO);

        //then
        assertThrows(UserAlreadyExistException.class, executable);
    }

    @Test
    void should_RegisterNewUserAccount() {
        //given
        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO();
        registeredUserDTO.setEmail("email@email.com");
        registeredUserDTO.setUsername("newUser");
        registeredUserDTO.setPassword("Password1.");
        registeredUserDTO.setPhoneNumber("123123123");
        registeredUserDTO.setUsageAcceptance(true);

        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setUsername(registeredUserDTO.getUsername());
        registeredUser.setPassword(passwordEncoderMock.encode(registeredUserDTO.getPassword()));
        registeredUser.setEmail(registeredUserDTO.getEmail());
        registeredUser.setPhoneNumber(registeredUserDTO.getPhoneNumber());
        registeredUser.setUsageAcceptance(registeredUserDTO.isUsageAcceptance());
        when(this.registeredUserDTOMapperMock
                .toRegisteredUser(registeredUserDTO))
                .thenReturn(registeredUser);
        Role role = new Role(1, "ROLE_USER");
        when(this.roleRepositoryMock.findByName("ROLE_USER")).thenReturn(role);


        //when
        registeredUserServiceImpl.registerNewUserAccount(registeredUserDTO);

        //then
        verify(registeredUserRepositoryMock).save(registeredUser);
        assertEquals(registeredUser.getEnabled(), 1);
        assertEquals(registeredUser.getRoles().size(), 1);
    }
}
