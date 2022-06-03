package pl.coderslab.reserveapooltable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;
import pl.coderslab.reserveapooltable.entity.ReservationsBasket;
import pl.coderslab.reserveapooltable.entity.User;
import pl.coderslab.reserveapooltable.repository.ReservationsBasketRepository;
import pl.coderslab.reserveapooltable.repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final ReservationsBasketRepository reservationsBasketRepository;

    public UserController(UserRepository userRepository, ReservationsBasketRepository reservationsBasketRepository) {
        this.userRepository = userRepository;
        this.reservationsBasketRepository = reservationsBasketRepository;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveUser(HttpServletRequest request,
                           @Valid User user,
                           BindingResult result) {
        String paymentMethod = request.getParameter("paymentMethod");
        if (result.hasErrors()) {
            return "reservation-summary";
        }
        userRepository.save(user);

        Optional<Cookie> cookieOptional = Optional.ofNullable(WebUtils.getCookie(request, "basketId"));
        if (cookieOptional.isPresent()) {
            Optional<ReservationsBasket> reservationsBasketOptional = reservationsBasketRepository.findById(Long.parseLong(cookieOptional.get().getValue()));
            if (reservationsBasketOptional.isPresent()) {
                ReservationsBasket reservationsBasket = reservationsBasketOptional.get();
                reservationsBasket.setUser(user);
                reservationsBasket.setPaymentMethod(paymentMethod);
                reservationsBasketRepository.save(reservationsBasket);
            }
        }
        return "redirect:/reservation/payment";
    }

    @ModelAttribute("reservationsBasket")
    public ReservationsBasket getReservationsToConfirm(HttpServletRequest request) {
        Optional<Cookie> cookieOptional = Optional.ofNullable(WebUtils.getCookie(request, "basketId"));
        if (cookieOptional.isPresent()) {
            Optional<ReservationsBasket> reservationsBasketOptional = reservationsBasketRepository.findById(Long.parseLong(cookieOptional.get().getValue()));
            if (reservationsBasketOptional.isPresent()) {
                return reservationsBasketOptional.get();
            }
        }

        return new ReservationsBasket();
    }
}
