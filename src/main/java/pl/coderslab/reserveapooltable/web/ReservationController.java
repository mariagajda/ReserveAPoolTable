package pl.coderslab.reserveapooltable.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.reserveapooltable.entity.Reservation;
import pl.coderslab.reserveapooltable.entity.TableToReserve;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;
import pl.coderslab.reserveapooltable.repository.TableToReserveRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/reserve")
public class ReservationController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TableToReserveRepository tableToReserveRepository;

    @GetMapping("/date")
    public String pickDate(Model model) {
        //model.addAttribute("price1")
        //model.addAttribute("price1")
        //model.addAttribute("price1")
        //model.addAttribute("price1")
        //model.addAttribute("price1")
        //model.addAttribute("price1")

        return "reservation-date";
    }

    @PostMapping("/date")
    public String saveDateAndPickTableAndHour(@RequestParam String dateStr, Model model) {
        model.addAttribute("date", dateStr);
        logger.info("Data: " + dateStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        model.addAttribute("tables", tableToReserveRepository.findAll().stream().sorted(Comparator.comparingInt(TableToReserve::getTableNumber)).collect(Collectors.toList()));
        model.addAttribute("reservations", reservationRepository.findAllByDateSorted(date));
        reservationRepository.findAllByDateSorted(date).stream()
                .forEach(item -> logger.info("start time: " + item.getStartTime()));
        return "reservation-tables-and-hours";
    }

    @GetMapping("/datetablesandhours")
    public String saveTablesAndHours(HttpServletRequest request, Model model) {
        String[] pickedReservationsId = request.getParameterValues("pickedReservations");
        List<Reservation> reservationsList = new ArrayList<>();
        for (int i = 0; i < pickedReservationsId.length; i++) {
            logger.info(pickedReservationsId[i]);
            reservationsList.add(reservationRepository.findById(Long.parseLong(pickedReservationsId[i])).get());
        }
        model.addAttribute("date", reservationsList.get(0).getDate());
        model.addAttribute("reservationsToConfirm", reservationsList);
        return "user-data";
    }

}
