package pl.coderslab.reserveapooltable.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.reserveapooltable.entity.*;
import pl.coderslab.reserveapooltable.enums.PriceGroup;
import pl.coderslab.reserveapooltable.repository.HolidayWorkdaysRepository;
import pl.coderslab.reserveapooltable.repository.PriceRepository;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;
import pl.coderslab.reserveapooltable.repository.TableToReserveRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addReservations(HttpServletRequest request) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate firstDay = LocalDate.parse(request.getParameter("firstDay"), dateFormatter);
        LocalDate lastDay = LocalDate.parse(request.getParameter("lastDay"), dateFormatter);
        LocalTime monToThuTimeFrom = LocalTime.parse(request.getParameter("monToThuTimeFrom"), timeFormatter);
        LocalTime friTimeFrom = LocalTime.parse(request.getParameter("friTimeFrom"), timeFormatter);
        LocalTime satTimeFrom = LocalTime.parse(request.getParameter("satTimeFrom"), timeFormatter);
        LocalTime sunTimeFrom = LocalTime.parse(request.getParameter("sunTimeFrom"), timeFormatter);
        LocalTime monToThuTimeTo = LocalTime.parse(request.getParameter("monToThuTimeTo"), timeFormatter);
        LocalTime friTimeTo = LocalTime.parse(request.getParameter("friTimeTo"), timeFormatter);
        LocalTime satTimeTo = LocalTime.parse(request.getParameter("satTimeTo"), timeFormatter);
        LocalTime sunTimeTo = LocalTime.parse(request.getParameter("sunTimeTo"), timeFormatter);
        long duration = Long.parseLong(request.getParameter("duration"));
        List<LocalDate> datesBetweenFirstAndLastDay = new ArrayList<>();
        for (int i = 0; i <= firstDay.until(lastDay, ChronoUnit.DAYS); i++) {
            datesBetweenFirstAndLastDay.add(firstDay.plusDays(i));
        }
        List<TableToReserve> tables = tableToReserveRepository.findAll();
        datesBetweenFirstAndLastDay.stream().forEach(date -> {
            LocalDateTime dateTimeFrom;
            LocalDateTime dateTimeTo;
            if (date.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                if (friTimeTo.getHour() > 6) {
                    dateTimeFrom = LocalDateTime.of(date, friTimeFrom);
                    dateTimeTo = LocalDateTime.of(date, friTimeTo);
                } else {
                    dateTimeFrom = LocalDateTime.of(date, friTimeFrom);
                    dateTimeTo = LocalDateTime.of(date.plusDays(1), friTimeTo);
                }
                long numberOfHours = dateTimeFrom.until(dateTimeTo, ChronoUnit.HOURS);
                long numberOfReservationsPerDay = numberOfHours * 60 / duration;
//                logger.info("dateTimeFrom: " + dateTimeFrom);
//                logger.info("dateTimeTo: " + dateTimeTo);
//                logger.info("numberOfHours" + numberOfHours);
//                logger.info("numberOfReservationsPerDay" + numberOfReservationsPerDay);

                for (int i = 0; i < numberOfReservationsPerDay; i++) {
//                    logger.info("dateTimeFrom: " + dateTimeFrom);
//                    logger.info("numberOfReservationsPerDay: " + numberOfReservationsPerDay);
                    for (int j = 0; j < tables.size(); j++) {
                        Reservation reservation = new Reservation(date, dateTimeFrom, dateTimeFrom.plusMinutes(duration), PriceGroup.FRI, tables.get(j));
                        countPrice(reservation, duration);
                        reservationRepository.save(reservation);
//                        logger.info("reservation.toString: " + reservation.toString());
                    }
                    dateTimeFrom = dateTimeFrom.plusMinutes(duration);
                }

            } else if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                if (friTimeTo.getHour() > 6) {
                    dateTimeFrom = LocalDateTime.of(date, satTimeFrom);
                    dateTimeTo = LocalDateTime.of(date, satTimeTo);
                } else {
                    dateTimeFrom = LocalDateTime.of(date, satTimeFrom);
                    dateTimeTo = LocalDateTime.of(date.plusDays(1), satTimeTo);
                }
                long numberOfHours = dateTimeFrom.until(dateTimeTo, ChronoUnit.HOURS);

                long numberOfReservationsPerDay = numberOfHours * 60 / duration;

                for (int i = 0; i < numberOfReservationsPerDay; i++) {

                    for (int j = 0; j < tables.size(); j++) {
                        Reservation reservation = new Reservation(date, dateTimeFrom, dateTimeFrom.plusMinutes(duration), PriceGroup.SAT, tables.get(j));
                        countPrice(reservation, duration);
                        reservationRepository.save(reservation);
                    }
                    dateTimeFrom = dateTimeFrom.plusMinutes(duration);
                }
            } else if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || holidayWorkdaysRepository.findAll().stream().map(HolidayWorkdays::getDate).collect(Collectors.toList()).contains(date)) {
                if (sunTimeTo.getHour() > 6) {
                    dateTimeFrom = LocalDateTime.of(date, sunTimeFrom);
                    dateTimeTo = LocalDateTime.of(date, sunTimeTo);
                } else {
                    dateTimeFrom = LocalDateTime.of(date, sunTimeFrom);
                    dateTimeTo = LocalDateTime.of(date.plusDays(1), sunTimeTo);
                }
                long numberOfHours = dateTimeFrom.until(dateTimeTo, ChronoUnit.HOURS);

                long numberOfReservationsPerDay = numberOfHours * 60 / duration;

                for (int i = 0; i < numberOfReservationsPerDay; i++) {
                    for (int j = 0; j < tables.size(); j++) {
                        Reservation reservation = new Reservation(date, dateTimeFrom, dateTimeFrom.plusMinutes(duration), PriceGroup.SUNandHOLIDAYS, tables.get(j));
                        countPrice(reservation, duration);
                        reservationRepository.save(reservation);
                    }
                    dateTimeFrom = dateTimeFrom.plusMinutes(duration);
                }
            } else {
                if (monToThuTimeTo.getHour() > 6) {
                    dateTimeFrom = LocalDateTime.of(date, monToThuTimeFrom);
                    dateTimeTo = LocalDateTime.of(date, monToThuTimeTo);
                } else {
                    dateTimeFrom = LocalDateTime.of(date, monToThuTimeFrom);
                    dateTimeTo = LocalDateTime.of(date.plusDays(1), monToThuTimeTo);
                }
                long numberOfHours = dateTimeFrom.until(dateTimeTo, ChronoUnit.HOURS);

                long numberOfReservationsPerDay = numberOfHours * 60 / duration;

                for (int i = 0; i < numberOfReservationsPerDay; i++) {
                    for (int j = 0; j < tables.size(); j++) {
                        Reservation reservation = new Reservation(date, dateTimeFrom, dateTimeFrom.plusMinutes(duration), PriceGroup.MONtoTHU, tables.get(j));
                        countPrice(reservation, duration);
                        reservationRepository.save(reservation);
                    }
                    dateTimeFrom = dateTimeFrom.plusMinutes(duration);
                }
            }
        });
        return "reservation/list";
    }

    @RequestMapping("/date")
    public String pickDate(Model model) {
        model.addAttribute("monToThursdayBefore18", priceRepository.findById(1L).get());
        model.addAttribute("monToThursdayAfter18", priceRepository.findById(2L).get());
        model.addAttribute("fridayBefore18", priceRepository.findById(3L).get());
        model.addAttribute("fridayAfter18", priceRepository.findById(4L).get());
        model.addAttribute("saturdayBefore18", priceRepository.findById(5L).get());
        model.addAttribute("saturdayAfter18", priceRepository.findById(6L).get());
        model.addAttribute("sundayHolidaysBefore18", priceRepository.findById(7L).get());
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
            logger.info("Reservation before user: " + reservation.toString());
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


    public void countPrice(Reservation reservation, long duration) {
        if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.FRI, false).getPricePerHour()) / (60/duration));
            } else {
                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.FRI, true).getPricePerHour() / (60/duration));
            }
        } else if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SAT, false).getPricePerHour()) / (60/duration));
            } else {
                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SAT, true).getPricePerHour() / (60/duration));
            }
        } else if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY) || holidayWorkdaysRepository.findAll().stream().map(HolidayWorkdays::getDate).collect(Collectors.toList()).contains(reservation.getDate())) {
            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SUNandHOLIDAYS, false).getPricePerHour()) / (60/duration));
            } else {
                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SUNandHOLIDAYS, true).getPricePerHour() / (60/duration));
            }
        } else {
            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.MONtoTHU, false).getPricePerHour()) / (60/duration));
            } else {
                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.MONtoTHU, true).getPricePerHour() / (60/duration));
            }
        }
    }
}
