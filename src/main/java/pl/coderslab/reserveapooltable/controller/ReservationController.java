package pl.coderslab.reserveapooltable.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;
import pl.coderslab.reserveapooltable.entity.*;
import pl.coderslab.reserveapooltable.repository.*;
import pl.coderslab.reserveapooltable.service.CurrentUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final TableToReserveRepository tableToReserveRepository;
    private final PriceRepository priceRepository;
    private final ReservationsBasketRepository reservationsBasketRepository;
    private final RegisteredUserRepository registeredUserRepository;

    public ReservationController(ReservationRepository reservationRepository, TableToReserveRepository tableToReserveRepository, PriceRepository priceRepository, ReservationsBasketRepository reservationsBasketRepository, RegisteredUserRepository registeredUserRepository) {
        this.reservationRepository = reservationRepository;
        this.tableToReserveRepository = tableToReserveRepository;
        this.priceRepository = priceRepository;
        this.reservationsBasketRepository = reservationsBasketRepository;
        this.registeredUserRepository = registeredUserRepository;
    }


    @RequestMapping("/date")
    public String pickDate(Model model) {
        if (priceRepository.findById(1L).isPresent()) {
            model.addAttribute("monToThursdayBefore18", priceRepository.findById(1L).get());
        }
        if (priceRepository.findById(2L).isPresent()) {
            model.addAttribute("monToThursdayAfter18", priceRepository.findById(2L).get());
        }
        if (priceRepository.findById(3L).isPresent()) {
            model.addAttribute("fridayBefore18", priceRepository.findById(3L).get());
        }
        if (priceRepository.findById(4L).isPresent()) {
            model.addAttribute("fridayAfter18", priceRepository.findById(4L).get());
        }
        if (priceRepository.findById(5L).isPresent()) {
            model.addAttribute("saturdayBefore18", priceRepository.findById(5L).get());
        }
        if (priceRepository.findById(6L).isPresent()) {
            model.addAttribute("saturdayAfter18", priceRepository.findById(6L).get());
        }
        if (priceRepository.findById(7L).isPresent()) {
            model.addAttribute("sundayHolidaysBefore18", priceRepository.findById(7L).get());
        }
        if (priceRepository.findById(8L).isPresent()) {
            model.addAttribute("sundayHolidaysAfter18", priceRepository.findById(8L).get());
        }
        if (reservationRepository.findAllSortByReservationDateDesc().size() == 0) {
            model.addAttribute("noReservations", true);
        } else {
            LocalDate lastReservationDateInDatabase = reservationRepository.findAllSortByReservationDateDesc().get(0).getDate();
            model.addAttribute("maxDate", lastReservationDateInDatabase);
            model.addAttribute("minDate", LocalDate.now().plusDays(1));
            model.addAttribute("noReservations", false);
        }
        return "reservation-date";
    }

    @RequestMapping(value = "/date", method = RequestMethod.POST)
    public String saveDateAndPickTableAndHour(@RequestParam String dateStr,
                                              Model model) {
        model.addAttribute("date", dateStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        model.addAttribute("tables", tableToReserveRepository.findAll().stream().sorted(Comparator.comparingInt(TableToReserve::getTableNumber)).collect(Collectors.toList()));
        model.addAttribute("reservations", reservationRepository.findAllByDateSorted(date));

        return "reservation-tables-and-hours";
    }

    @RequestMapping(value = "/datetablesandhours", method = RequestMethod.POST)
    public String saveTablesAndHours(HttpServletRequest request,
                                     @AuthenticationPrincipal CurrentUser customUser,
                                     HttpServletResponse response) {

        String[] pickedReservationsId = request.getParameterValues("pickedReservations");
        List<Reservation> reservationsList = new ArrayList<>();
        double priceSum = 0.0;
        Arrays.stream(pickedReservationsId).forEach(id -> {
            Optional<Reservation> reservationOptional = reservationRepository.findById(Long.parseLong(id));
            reservationOptional.ifPresent(reservationsList::add);
        });
        for (Reservation r : reservationsList) {
            priceSum += r.getPricePerReservation();
        }
        LocalDate date = reservationsList.get(0).getDate();
        ReservationsBasket reservationsBasket = new ReservationsBasket(date, priceSum, reservationsList);
        Optional<RegisteredUser> user = Optional.ofNullable(customUser).map(CurrentUser::getUser);
        user.ifPresent(reservationsBasket::setUser);
        reservationsBasketRepository.save(reservationsBasket);
        Cookie cookie = new Cookie("basketId", String.valueOf(reservationsBasket.getId()));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/reservation/summary";
    }

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "reservation-summary";
    }

    @RequestMapping("/payment")
    public String setPaymentMethod(HttpServletRequest request) {
        String paymentMethodStr = request.getParameter("paymentMethod");
        Optional<Cookie> cookieOptional = Optional.ofNullable(WebUtils.getCookie(request, "basketId"));
        if (cookieOptional.isPresent()) {
            Optional<ReservationsBasket> reservationsBasketOptional = reservationsBasketRepository.findById(Long.parseLong(cookieOptional.get().getValue()));
            if (reservationsBasketOptional.isPresent()) {
                ReservationsBasket reservationsBasket = reservationsBasketOptional.get();
                if (reservationsBasket.getPaymentMethod() != null) {
                    if (reservationsBasket.getPaymentMethod().equals("transfer")) {
                        return "redirect:/reservation/payment/transfer";
                    } else {
                        return "redirect:/reservation/payment/inPlace";
                    }
                } else {
                    reservationsBasket.setPaymentMethod(paymentMethodStr);
                    reservationsBasketRepository.save(reservationsBasket);
                    if (paymentMethodStr.equals("transfer")) {
                        return "redirect:/reservation/payment/transfer";
                    } else {
                        return "redirect:/reservation/payment/inPlace";
                    }
                }
            }
        }
        return "redirect:/reservation/summary";
    }


    @RequestMapping(value = "/payment/transfer")
    public String payForReservationOnline() {

        return "payment-online";
    }

    @RequestMapping(value = "/payment/inPlace")
    public String payForReservationLater() {

        return "payment-in-place";
    }


    @RequestMapping(value = "/succeeded")
    public String reserveWithSuccess(HttpServletRequest request) {
        Optional<Cookie> cookieOptional = Optional.ofNullable(WebUtils.getCookie(request, "basketId"));
        if (cookieOptional.isPresent()) {
            Optional<ReservationsBasket> reservationsBasketOptional = reservationsBasketRepository.findById(Long.parseLong(cookieOptional.get().getValue()));
            if (reservationsBasketOptional.isPresent()) {
                ReservationsBasket reservationsBasket = reservationsBasketOptional.get();

                reservationsBasket.getReservations().stream().forEach(reservation -> {
                    reservation.setAvailable(false);
                    reservation.setUser(reservationsBasket.getUser());
                    reservationRepository.save(reservation);
                });
                reservationsBasket.setConfirmed(true);
                if (reservationsBasket.getPaymentMethod().equals("transfer")) {
                    reservationsBasket.setPaid(true);
                }
                reservationsBasketRepository.save(reservationsBasket);
                User user = reservationsBasket.getUser();
                if (user instanceof RegisteredUser) {
                    RegisteredUser registeredUser = (RegisteredUser) user;
                    registeredUser.setReservationsCounter(registeredUser.getReservationsCounter() + 1);
                    List<ReservationsBasket> reservationsHistory = registeredUser.getReservationsHistory();
                    reservationsHistory.add(reservationsBasket);
                    if (registeredUser.getReservationsCounter() >= 5) {
                        registeredUser.setDiscount(0.05);
                    }
                    registeredUserRepository.save(registeredUser);
                }
            }
        }

        return "reservation-succeeded";
    }

    @RequestMapping(value = "/failed")
    public String reserveWithoutSuccess() {
        return "reservation-failed";
    }

    @RequestMapping(value = "/deleteCookie")
    public String reserveWithoutSuccess(HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> cookieOptional = Optional.ofNullable(WebUtils.getCookie(request, "basketId"));
        if (cookieOptional.isPresent()) {
            Cookie cookie = cookieOptional.get();
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/reservation/date";
        }
        return "redirect:/reservation/date";
    }


    @ModelAttribute("reservationsBasket")
    public ReservationsBasket getReservationsToConfirm(HttpServletRequest request) {
        Optional<Cookie> cookieOptional = Optional.ofNullable(WebUtils.getCookie(request, "basketId"));
        if (cookieOptional.isPresent()) {
            long basketId = Long.parseLong(cookieOptional.get().getValue());
            if (reservationsBasketRepository.findById(basketId).isPresent()) {
                return reservationsBasketRepository.findById(basketId).get();
            }
        }
        return new ReservationsBasket();
    }


}
