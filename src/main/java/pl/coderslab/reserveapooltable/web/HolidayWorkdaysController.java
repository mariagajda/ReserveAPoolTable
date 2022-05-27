package pl.coderslab.reserveapooltable.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.reserveapooltable.entity.HolidayWorkday;
import pl.coderslab.reserveapooltable.repository.HolidayWorkdaysRepository;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/holiday-workdays")
public class HolidayWorkdaysController {

    private final HolidayWorkdaysRepository holidayWorkdaysRepository;
    private final ReservationRepository reservationRepository;

    public HolidayWorkdaysController(HolidayWorkdaysRepository holidayWorkdaysRepository, ReservationRepository reservationRepository) {
        this.holidayWorkdaysRepository = holidayWorkdaysRepository;
        this.reservationRepository = reservationRepository;
    }

    @RequestMapping("/list")
    public String showHolidayWorkdays(Model model) {
        model.addAttribute("holidayWorkdaysList", holidayWorkdaysRepository.findAll());
        return "admin/holiday-workdays-list";
    }

    @RequestMapping("/add")
    public String addHolidayWorkday(Model model) {
        model.addAttribute("holidayWorkday", new HolidayWorkday());
        LocalDate lastReservationDateInDatabase = reservationRepository.findAllSortByReservationDateDesc().get(0).getDate();
        model.addAttribute("minDate", lastReservationDateInDatabase.plusDays(1));
        return "admin/holiday-workday-add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addHolidayWorkday(@Valid HolidayWorkday holidayWorkday, BindingResult result) {
        if(result.hasErrors()){
            return "admin/holiday-workday-add";
        }
        holidayWorkdaysRepository.save(holidayWorkday);
        return "redirect:/holiday-workdays/list";
    }

    @RequestMapping("/delete/{id}")
    public String removeHolidayWorkday(@PathVariable Long id) {
        holidayWorkdaysRepository.deleteById(id);
        return "redirect:/holiday-workdays/list";
    }

}
