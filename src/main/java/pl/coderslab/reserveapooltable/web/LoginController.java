package pl.coderslab.reserveapooltable.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;

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

}
