package pl.coderslab.reserveapooltable.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.reserveapooltable.entity.HolidayWorkdays;
import pl.coderslab.reserveapooltable.repository.HolidayWorkdaysRepository;

import javax.validation.Valid;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/holiday-workdays")
public class HolidayWorkdaysController {
    @Autowired
    HolidayWorkdaysRepository holidayWorkdaysRepository;

    @RequestMapping("/list")
    public String showHolidayWorkdays(Model model) {
        model.addAttribute("holidayWorkdaysList", holidayWorkdaysRepository.findAll());
        return "admin/holiday-workdays-list";
    }

    @RequestMapping("/add")
    public String addHolidayWorkday(Model model) {
        model.addAttribute("holidayWorkday", new HolidayWorkdays());
        return "admin/holiday-workday-add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addHolidayWorkday(Model model, @Valid HolidayWorkdays holidayWorkdays, BindingResult result) {
        if(result.hasErrors()){
            return "admin/holiday-workday-add";
        }
        holidayWorkdaysRepository.save(holidayWorkdays);
        return "redirect:/holiday-workdays/list";
    }

    @RequestMapping("/delete/{id}")
    public String removeHolidayWorkday(Model model, @PathVariable Long id) {
        holidayWorkdaysRepository.deleteById(id);
        return "holiday-workdays/list";
    }

}
