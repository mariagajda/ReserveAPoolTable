package pl.coderslab.reserveapooltable.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;
import pl.coderslab.reserveapooltable.entity.ReservationsBasket;
import pl.coderslab.reserveapooltable.entity.User;
import pl.coderslab.reserveapooltable.repository.RegisteredUserRepository;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;
import pl.coderslab.reserveapooltable.repository.ReservationsBasketRepository;
import pl.coderslab.reserveapooltable.repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final RegisteredUserRepository registeredUserRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationsBasketRepository reservationsBasketRepository;

    public UserController(RegisteredUserRepository registeredUserRepository, ReservationRepository reservationRepository, UserRepository userRepository, ReservationsBasketRepository reservationsBasketRepository) {
        this.registeredUserRepository = registeredUserRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.reservationsBasketRepository = reservationsBasketRepository;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveUser(HttpServletRequest request,
                           @Valid User user,
                           BindingResult result) {
        String paymentMethod = request.getParameter("paymentMethod");
        if (result.hasErrors()) {
            return "user-register";
        }
        userRepository.save(user);

        Cookie cookie = WebUtils.getCookie(request, "basketId");
        ReservationsBasket reservationsBasket = reservationsBasketRepository.findById(Long.parseLong(cookie.getValue())).get();
        reservationsBasket.setUser(user);
        reservationsBasket.setPaymentMethod(paymentMethod);
        reservationsBasketRepository.save(reservationsBasket);
        return "redirect:/reservation/payment";
    }
    @ModelAttribute("reservationsBasket")
    public ReservationsBasket getReservationsToConfirm(HttpServletRequest request) {
        Optional<Cookie> cookieOptional = Optional.ofNullable(WebUtils.getCookie(request, "basketId"));
        if(!cookieOptional.equals(Optional.empty())){
            return reservationsBasketRepository.findById(Long.parseLong(cookieOptional.get().getValue())).get();
        } else return new ReservationsBasket();
    }






}
