package pl.coderslab.reserveapooltable.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.reserveapooltable.DTO.RegisteredUserDTO;
import pl.coderslab.reserveapooltable.entity.RegisteredUser;
import pl.coderslab.reserveapooltable.errors.UserAlreadyExistException;
import pl.coderslab.reserveapooltable.repository.RegisteredUserRepository;
import pl.coderslab.reserveapooltable.service.RegisteredUserService;
import pl.coderslab.reserveapooltable.service.RegisteredUserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisteredUserController {
    private final RegisteredUserServiceImpl registeredUserServiceImpl;
    private final RegisteredUserRepository registeredUserRepository;

    public RegisteredUserController(RegisteredUserServiceImpl registeredUserServiceImpl, RegisteredUserRepository registeredUserRepository){
        this.registeredUserServiceImpl = registeredUserServiceImpl;
        this.registeredUserRepository = registeredUserRepository;
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
        try{
            RegisteredUser registered = registeredUserServiceImpl.registerNewUserAccount(registeredUserDTO);
        } catch (UserAlreadyExistException uaeEx){
//            mav.addObject("message", "An account for that username/email already exists.");
            return "/errors/registereduser-already-exist";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/login")
    public String showLoginForm(){
        return "login";
    }
//    @RequestMapping(value = "/user/login")
//    public String redirectUserLogin(){
//        return "redirect:/reservation/date";
//    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String redirectLogin(){
//        return "redirect:/reservation/date";
//    }
//
//    @RequestMapping("/user/log-failed")
//    public String showUserLogFailedView(){
//        return "user-log-failed";
//    }


}
