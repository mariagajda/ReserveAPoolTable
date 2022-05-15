package pl.coderslab.reserveapooltable.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.reserveapooltable.entity.HolidayWorkdays;
import pl.coderslab.reserveapooltable.entity.Reservation;
import pl.coderslab.reserveapooltable.entity.TableToReserve;
import pl.coderslab.reserveapooltable.entity.User;
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
@SessionAttributes("reservationsToConfirm")
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
        Arrays.stream(pickedReservationsId).forEach(id -> {
            Reservation reservation = reservationRepository.findById(Long.parseLong(id)).get();
            countPrice(reservation);
            reservationRepository.save(reservation);
            reservationsList.add(reservation);
        });

        model.addAttribute("date", reservationsList.get(0).getDate());
        model.addAttribute("reservationsToConfirm", reservationsList);
        return "redirect:/user/add";
    }

    @RequestMapping(value = "/confirm")
    public String confirmReservation(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Reservation> reservationsToConfirm = (List<Reservation>) session.getAttribute("reservationsToConfirm");
        reservationsToConfirm.stream().forEach(reservation -> {
            reservation.setUser(user);
            reservationRepository.save(reservation);
        });
        return "redirect:/reservation/payment";
    }

    @RequestMapping(value = "/payment")
    @ResponseBody
    public String payForReservation() {
        return "payment-online";
    }



    public void countPrice(Reservation reservation) {
        if(reservation.getDate().getDayOfWeek().equals(DayOfWeek.FRIDAY) && reservation.getStartTime().isBefore(LocalTime.of(18, 00))){
            reservation.setPricePerReservation((priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour())/2);
        }
        else if(reservation.getDate().getDayOfWeek().equals(DayOfWeek.FRIDAY) && reservation.getStartTime().isAfter(LocalTime.of(17, 59))){
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(18, 0)).getPricePerHour()/2);
        }
        else if(reservation.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) && reservation.getStartTime().isBefore(LocalTime.of(18, 00))){
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour()/2);
        }
        else if(reservation.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) && reservation.getStartTime().isAfter(LocalTime.of(17, 59))){
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour()/2);
        }
        else if(reservation.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY) && reservation.getStartTime().isBefore(LocalTime.of(18, 00))){
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour()/2);
        }
        else if(reservation.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY) && reservation.getStartTime().isAfter(LocalTime.of(17, 59))){
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour()/2);
        }
        else if(holidayWorkdaysRepository.findAll().stream().map(HolidayWorkdays::getDate).collect(Collectors.toList()).contains(reservation.getDate()) && reservation.getStartTime().isBefore(LocalTime.of(18, 00))){
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour()/2);
        }
        else if(holidayWorkdaysRepository.findAll().stream().map(HolidayWorkdays::getDate).collect(Collectors.toList()).contains(reservation.getDate()) && reservation.getStartTime().isAfter(LocalTime.of(17, 59))){
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Friday", LocalTime.of(0, 0)).getPricePerHour()/2);
        }
        else if(reservation.getStartTime().isBefore(LocalTime.of(18, 00))){
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Monday-Thursday", LocalTime.of(0, 0)).getPricePerHour()/2);
        }
        else{
            reservation.setPricePerReservation(priceRepository.findByDayOfWeekAndStartTime("Monday-Thursday", LocalTime.of(0, 0)).getPricePerHour()/2);
        }
    }

}
