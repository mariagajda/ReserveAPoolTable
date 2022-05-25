package pl.coderslab.reserveapooltable.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/")
    public String showHomepage(){
        return "homepage";
    }

    @RequestMapping(value = "/login")
    public String showLoginForm() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logOut(){
        return "/logout";
    }

    //    @RequestMapping("/user/log-failed")
//    public String showUserLogFailedView(){
//        return "user-log-failed";
//    }
}
