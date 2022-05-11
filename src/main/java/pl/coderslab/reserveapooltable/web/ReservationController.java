package pl.coderslab.reserveapooltable.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.reserveapooltable.entity.TableToReserve;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;
import pl.coderslab.reserveapooltable.repository.TableToReserveRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
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
    public String pickDate() {
        return "reservation-date";
    }

    @PostMapping("/date")
    public String saveDate(@RequestParam String dateStr, Model model) {
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

}
