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
import pl.coderslab.reserveapooltable.entity.RegisteredUser;
import pl.coderslab.reserveapooltable.entity.Reservation;
import pl.coderslab.reserveapooltable.entity.User;
import pl.coderslab.reserveapooltable.repository.RegisteredUserRepository;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;
import pl.coderslab.reserveapooltable.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveUser(Model model, HttpServletRequest request, @Valid User user, BindingResult result) {
        String[] reservationsToConfirm = request.getParameterValues("reservationsIdList");
        String paymentMethod = request.getParameter("paymentMethod");
        if (result.hasErrors()) {
            return "user-data";
        }
        userRepository.save(user);
        List<Reservation> reservationsToConfirmList = new ArrayList<>();

        Arrays.stream(reservationsToConfirm).forEach(reservationId -> {
            Reservation reservation = reservationRepository.findById(Long.parseLong(reservationId)).get();
            reservationsToConfirmList.add(reservation);
        });
        model.addAttribute("user", user);
        model.addAttribute("reservationsToConfirm", reservationsToConfirmList);
        if (paymentMethod.equals("transfer")) {
            return "redirect:/reservation/payment/transfer";
        } else {
            return "redirect:/reservation/payment/inPlace";
        }
    }

    @RequestMapping("/log")
    public String logUser() {
        return "login";
    }

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public String checkUserLog(Model model, HttpServletRequest request) {
        String[] reservationsToConfirm = request.getParameterValues("reservationsIdList");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        RegisteredUser registeredUser = registeredUserRepository.findUserByEmail(email);
        if (!email.equals(registeredUser.getEmail()) || !password.equals(registeredUser.getPassword())) {
            return "user-log-failed";
        }

        List<Reservation> reservationsToConfirmList = new ArrayList<>();
        Arrays.stream(reservationsToConfirm).forEach(reservationId -> {
            Reservation reservation = reservationRepository.findById(Long.parseLong(reservationId)).get();
            reservationsToConfirmList.add(reservation);
        });
        model.addAttribute("user", registeredUser);
        model.addAttribute("reservationsToConfirm", reservationsToConfirmList);

        return "redirect:/reservation/details";
    }


    @RequestMapping("/register")
    public String registerNewUser(Model model) {
        model.addAttribute("registeredUser", new RegisteredUser());
        return "user-register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveRegisteredUser(@Valid RegisteredUser registeredUser, BindingResult result) {
        if (result.hasErrors()) {
            return "user-register";
        }
        registeredUserRepository.save(registeredUser);
        return "redirect:/user/log";
    }


}
