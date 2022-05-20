package pl.coderslab.reserveapooltable.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ReservationRepository reservationRepository;

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
}
