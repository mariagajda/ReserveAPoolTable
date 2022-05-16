package pl.coderslab.reserveapooltable.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ReservationRepository reservationRepository;

    @RequestMapping("/management-panel")
    public String manageReservations(Model model){
        model.addAttribute("lastReservationdate", reservationRepository.findAllByReservationDateDesc().get(0));
        return "admin-management-panel";
    }
}
