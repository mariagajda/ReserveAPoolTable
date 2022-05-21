package pl.coderslab.reserveapooltable.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.reserveapooltable.entity.HolidayWorkdays;
import pl.coderslab.reserveapooltable.entity.Reservation;
import pl.coderslab.reserveapooltable.entity.TableToReserve;
import pl.coderslab.reserveapooltable.enums.PriceGroup;
import pl.coderslab.reserveapooltable.repository.HolidayWorkdaysRepository;
import pl.coderslab.reserveapooltable.repository.PriceRepository;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;
import pl.coderslab.reserveapooltable.repository.TableToReserveRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    TableToReserveRepository tableToReserveRepository;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    HolidayWorkdaysRepository holidayWorkdaysRepository;

    @RequestMapping("/management-panel")
    public String manageReservations(Model model){
        if(reservationRepository.findAllSortByReservationDateDesc().size() == 0){
            model.addAttribute("lastReservationDateWarning", "*No reservations - database is empty*");
            model.addAttribute("firstReservationDateTime", LocalDateTime.now());
        }
         else{
             LocalDate lastReservationDateInDatabase = reservationRepository.findAllSortByReservationDateDesc().get(0).getDate();
            model.addAttribute("lastReservationDateInDatabase", lastReservationDateInDatabase);
            model.addAttribute("firstReservationDate", LocalDate.from(lastReservationDateInDatabase).plusDays(1));
        }

        return "admin-management-panel";
    }

    @RequestMapping(value = "/reservations/add", method = RequestMethod.POST)
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
        return "/admin/management-panel";
    }

//    @RequestMapping ("/reservations/list")
//    public String showReservations(){
//        return "reservations-list";
//    }

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
