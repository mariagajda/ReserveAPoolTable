package pl.coderslab.reserveapooltable.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;
import pl.coderslab.reserveapooltable.entity.RegisteredUser;
import pl.coderslab.reserveapooltable.entity.ReservationsBasket;
import pl.coderslab.reserveapooltable.entity.User;
import pl.coderslab.reserveapooltable.repository.RegisteredUserRepository;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;
import pl.coderslab.reserveapooltable.repository.ReservationsBasketRepository;
import pl.coderslab.reserveapooltable.repository.UserRepository;
import pl.coderslab.reserveapooltable.service.RegisteredUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@SessionAttributes({"reservationsToConfirm", "user", "priceSum"})
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    RegisteredUserRepository registeredUserRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RegisteredUserService registeredUserService;
    @Autowired
    ReservationsBasketRepository reservationsBasketRepository;

//    @RequestMapping("/create")
//    @ResponseBody
//    public String createRegisteredUser(){
//        RegisteredUser registeredUser = new RegisteredUser();
//        registeredUser.setUsername("admin");
//        registeredUser.setPassword("admin");
//        registeredUserService.saveRegisteredUser(registeredUser);
//        return "admin";
//    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveUser(Model model, HttpServletRequest request, @Valid User user, BindingResult result) {
        String[] reservationsToConfirm = request.getParameterValues("reservationsIdList");
        String paymentMethod = request.getParameter("paymentMethod");
        if (result.hasErrors()) {
            return "user-data";
        }
        userRepository.save(user);

        Cookie cookie = WebUtils.getCookie(request, "basketId");
        ReservationsBasket reservationsBasket = reservationsBasketRepository.findById(Long.parseLong(cookie.getValue())).get();
        reservationsBasket.setUser(user);

        model.addAttribute("user", user);
        model.addAttribute("reservationsToConfirm", reservationsBasket.getReservations());
        model.addAttribute("paymentMethod", paymentMethod);
        return "redirect:/payment";
    }






}
