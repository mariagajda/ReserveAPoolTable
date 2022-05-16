package pl.coderslab.reserveapooltable.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.reserveapooltable.entity.*;
import pl.coderslab.reserveapooltable.repository.HolidayWorkdaysRepository;
import pl.coderslab.reserveapooltable.repository.PriceRepository;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;
import pl.coderslab.reserveapooltable.repository.TableToReserveRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes({"reservationsToConfirm", "user", "priceSum"})
@RequestMapping("/reservation")
public class ReservationController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TableToReserveRepository tableToReserveRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private HolidayWorkdaysRepository holidayWorkdaysRepository;

    @RequestMapping("/add")
    public String addReservations(){
        return "reservations-add-form";
    }

    @RequestMapping("/date")
    public String pickDate(Model model) {
        model.addAttribute("monToThursdayBefore18", priceRepository.findById(1L).get());
        model.addAttribute("fridayBefore18", priceRepository.findById(2L).get());
        model.addAttribute("saturdayBefore18", priceRepository.findById(3L).get());
        model.addAttribute("sundayHolidaysBefore18", priceRepository.findById(4L).get());
        model.addAttribute("monToThursdayAfter18", priceRepository.findById(5L).get());
        model.addAttribute("fridayAfter18", priceRepository.findById(6L).get());
        model.addAttribute("saturdayAfter18", priceRepository.findById(7L).get());
        model.addAttribute("sundayHolidaysAfter18", priceRepository.findById(8L).get());
        return "reservation-date";
    }

    @RequestMapping(value = "/date", method = RequestMethod.POST)
    public String saveDateAndPickTableAndHour(@RequestParam String dateStr, Model model) {
        model.addAttribute("date", dateStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        model.addAttribute("tables", tableToReserveRepository.findAll().stream().sorted(Comparator.comparingInt(TableToReserve::getTableNumber)).collect(Collectors.toList()));
        model.addAttribute("reservations", reservationRepository.findAllByDateSorted(date));

        return "reservation-tables-and-hours";
    }

    @RequestMapping(value = "/datetablesandhours", method = RequestMethod.POST)
    public String saveTablesAndHours(HttpServletRequest request, Model model) {
        String[] pickedReservationsId = request.getParameterValues("pickedReservations");
        List<Reservation> reservationsList = new ArrayList<>();
        double priceSum = 0.0;
        Arrays.stream(pickedReservationsId).forEach(id -> {
            Reservation reservation = reservationRepository.findById(Long.parseLong(id)).get();
            countPrice(reservation);
            reservationRepository.save(reservation);
            reservationsList.add(reservation);
        });
        for (Reservation r : reservationsList) {
            priceSum += r.getPricePerReservation();
        }

        model.addAttribute("date", reservationsList.get(0).getDate());
        model.addAttribute("reservationsToConfirm", reservationsList);
        model.addAttribute("priceSum", priceSum);
        return "redirect:/reservation/summary/userData";
    }

    @RequestMapping(value = "/summary/userData", method = RequestMethod.GET)
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "reservation-summary-user-data";
    }

    @RequestMapping("/details")
    public String showReservationDetails() {

        return "user-reservation-details";
    }

    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public String confirmReservation(HttpServletRequest request) {
        String paymentMethod = request.getParameter("paymentMethod");
        if (paymentMethod.equals("transfer")) {
            return "redirect:/reservation/payment/transfer";
        } else {
            return "redirect:/reservation/payment/inPlace";
        }
    }


    @RequestMapping(value = "/payment/transfer")
    public String payForReservationOnline(Model model) {

        return "payment-online";
    }

    @RequestMapping(value = "/payment/inPlace")
    public String payForReservationLater(Model model) {

        return "payment-in-place";
    }

    @RequestMapping(value = "/payment/succeeded")
    public String payWithSuccess(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Reservation> reservationsToConfirm = (List<Reservation>) session.getAttribute("reservationsToConfirm");
        reservationsToConfirm.stream().forEach(reservation -> {
            reservation.setUser(user);
            reservation.setAvailable(false);
            reservationRepository.save(reservation);
            if (user instanceof RegisteredUser) {
                RegisteredUser registeredUser = (RegisteredUser) user;
                registeredUser.setReservationsCounter(registeredUser.getReservationsCounter() + 1);
            }
        });

        return "reservation-succeeded";
    }

    @RequestMapping(value = "/payment-in-place/succeeded")
    public String payInPlaceReservationSuccedded(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Reservation> reservationsToConfirm = (List<Reservation>) session.getAttribute("reservationsToConfirm");
        reservationsToConfirm.stream().forEach(reservation -> {
            reservation.setUser(user);
            reservation.setAvailable(false);
            reservationRepository.save(reservation);
            if (user instanceof RegisteredUser) {
                RegisteredUser registeredUser = (RegisteredUser) user;
                registeredUser.setReservationsCounter(registeredUser.getReservationsCounter() + 1);
            }
        });

        return "reservation-succeeded";
    }

    @RequestMapping(value = "/payment/failed")
    public String payWithoutSuccess() {
        return "payment-failed";
    }


    public void countPrice(Reservation reservation) {
        if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.FRIDAY) && reservation.getStartTime().isBefore(LocalTime.of(18, 00))) {
            reservation.setPricePerReservation((priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour()) / 2);
        } else if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.FRIDAY) && reservation.getStartTime().isAfter(LocalTime.of(17, 59))) {
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(18, 0)).getPricePerHour() / 2);
        } else if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) && reservation.getStartTime().isBefore(LocalTime.of(18, 00))) {
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour() / 2);
        } else if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) && reservation.getStartTime().isAfter(LocalTime.of(17, 59))) {
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour() / 2);
        } else if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY) && reservation.getStartTime().isBefore(LocalTime.of(18, 00))) {
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour() / 2);
        } else if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY) && reservation.getStartTime().isAfter(LocalTime.of(17, 59))) {
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour() / 2);
        } else if (holidayWorkdaysRepository.findAll().stream().map(HolidayWorkdays::getDate).collect(Collectors.toList()).contains(reservation.getDate()) && reservation.getStartTime().isBefore(LocalTime.of(18, 00))) {
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour() / 2);
        } else if (holidayWorkdaysRepository.findAll().stream().map(HolidayWorkdays::getDate).collect(Collectors.toList()).contains(reservation.getDate()) && reservation.getStartTime().isAfter(LocalTime.of(17, 59))) {
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour() / 2);
        } else if (reservation.getStartTime().isBefore(LocalTime.of(18, 00))) {
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Monday-Thursday", LocalTime.of(0, 0)).getPricePerHour() / 2);
        } else {
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Monday-Thursday", LocalTime.of(0, 0)).getPricePerHour() / 2);
        }
    }

}
