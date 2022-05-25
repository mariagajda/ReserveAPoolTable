package pl.coderslab.reserveapooltable.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.reserveapooltable.DTO.RegisteredUserDTO;
import pl.coderslab.reserveapooltable.entity.RegisteredUser;
import pl.coderslab.reserveapooltable.entity.Role;
import pl.coderslab.reserveapooltable.errors.UserAlreadyExistException;
import pl.coderslab.reserveapooltable.repository.RegisteredUserRepository;
import pl.coderslab.reserveapooltable.repository.RoleRepository;
import pl.coderslab.reserveapooltable.service.RegisteredUserService;
import pl.coderslab.reserveapooltable.service.RegisteredUserServiceImpl;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class RegisteredUserController {
    private static final Logger logger = LoggerFactory.getLogger(RegisteredUserController.class);
    private final RegisteredUserServiceImpl registeredUserServiceImpl;
    private final RegisteredUserRepository registeredUserRepository;
    private final RegisteredUserService registeredUserService;
    private final RoleRepository roleRepository;

    public RegisteredUserController(RegisteredUserServiceImpl registeredUserServiceImpl, RegisteredUserRepository registeredUserRepository, RoleRepository roleRepository, RegisteredUserService registeredUserService) {
        this.registeredUserServiceImpl = registeredUserServiceImpl;
        this.registeredUserRepository = registeredUserRepository;
        this.roleRepository = roleRepository;
        this.registeredUserService = registeredUserService;
    }

    @RequestMapping("/create")
    @ResponseBody
    public String createRegisteredUser() {
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setUsername("admin");
        registeredUser.setPassword("Admin123!");
        registeredUser.setEnabled(1);
        registeredUser.setEmail("admin@email.com");
        registeredUser.setPhoneNumber("123234345");
        registeredUser.setUsageAcceptance(true);
        Role userRole = roleRepository.findByName("ROLE_ADMIN");
        Set<Role> rolesSet = new HashSet<>(Arrays.asList(userRole));
        rolesSet.add(userRole);
        registeredUser.setRoles(rolesSet);

        registeredUserService.saveRegisteredUser(registeredUser);
        for(Role r : registeredUser.getRoles()){
            logger.info("REGISTERED USER ROLES: " + r.toString());
        }

        return "admin";
    }

    @RequestMapping("/user/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registeredUserDTO", new RegisteredUserDTO());
        return "user-register";
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String registerUserAccount(@Valid RegisteredUserDTO registeredUserDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "user-register";
        }
        try {
            RegisteredUser registered = registeredUserServiceImpl.registerNewUserAccount(registeredUserDTO);
        } catch (UserAlreadyExistException uaeEx) {
//            mav.addObject("message", "An account for that username/email already exists.");
            return "/errors/registereduser-already-exist";
        }
        return "redirect:/login";
    }


}
