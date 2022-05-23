package pl.coderslab.reserveapooltable.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.reserveapooltable.entity.Price;
import pl.coderslab.reserveapooltable.repository.PriceRepository;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceRepository priceRepository;

    @RequestMapping("/list")
    public String showPriceList(Model model) {
        model.addAttribute("monToThursdayBefore18", priceRepository.findById(1L).get());
        model.addAttribute("fridayBefore18", priceRepository.findById(2L).get());
        model.addAttribute("saturdayBefore18", priceRepository.findById(3L).get());
        model.addAttribute("sundayHolidaysBefore18", priceRepository.findById(4L).get());
        model.addAttribute("monToThursdayAfter18", priceRepository.findById(5L).get());
        model.addAttribute("fridayAfter18", priceRepository.findById(6L).get());
        model.addAttribute("saturdayAfter18", priceRepository.findById(7L).get());
        model.addAttribute("sundayHolidaysAfter18", priceRepository.findById(8L).get());
        return "admin/price-list";
    }

   @RequestMapping("/edit/{id}")
    public String editPrice(Model model, @PathVariable Long id){
        model.addAttribute("price", priceRepository.findById(id).get());
        return "admin/price-edit";
   }

   @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public String savePrice(@PathVariable Long id, @Valid Price price, BindingResult result){
        if(result.hasErrors()){
            return "admin/price-edit";
        }
        priceRepository.save(price);
        return "redirect:/price/list";
   }



}
