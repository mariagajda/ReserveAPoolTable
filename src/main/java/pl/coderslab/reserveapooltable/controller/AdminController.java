package pl.coderslab.reserveapooltable.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.reserveapooltable.stopwatch.StopWatch;
import pl.coderslab.reserveapooltable.entity.ReservationParameters;
import pl.coderslab.reserveapooltable.entity.TableToReserve;
import pl.coderslab.reserveapooltable.repository.*;
import pl.coderslab.reserveapooltable.service.ReservationParametersService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final ReservationRepository reservationRepository;
    private final ReservationParametersRepository reservationParametersRepository;
    private final ReservationParametersService reservationParametersService;
    private final TableToReserveRepository tableToReserveRepository;

    public AdminController(ReservationRepository reservationRepository, ReservationParametersRepository reservationParametersRepository, ReservationParametersService reservationParametersService, TableToReserveRepository tableToReserveRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationParametersRepository = reservationParametersRepository;
        this.reservationParametersService = reservationParametersService;
        this.tableToReserveRepository = tableToReserveRepository;
    }


    @RequestMapping("/management-panel")
    public String manageReservations(Model model) {
        ReservationParameters reservationParameters = new ReservationParameters();
        model.addAttribute("reservationParameters", reservationParameters);
        if (reservationRepository.findAllSortByReservationDateDesc().size() == 0) {
            model.addAttribute("lastReservationDateWarning", "*No reservations - database is empty*");
            model.addAttribute("firstReservationDate", LocalDate.now().plusDays(1));
        } else {
            LocalDate lastReservationDateInDatabase = reservationRepository.findAllSortByReservationDateDesc().get(0).getDate();
            model.addAttribute("lastReservationDateInDatabase", lastReservationDateInDatabase);
            model.addAttribute("firstReservationDate", LocalDate.from(lastReservationDateInDatabase).plusDays(1));
            model.addAttribute("minFrom", lastReservationDateInDatabase.plusDays(1));
            model.addAttribute("minTo", lastReservationDateInDatabase.plusDays(1));
        }

        return "admin/management-panel";
    }

    @RequestMapping(value = "/management-panel", method = RequestMethod.POST)
    public String addReservations(@Valid ReservationParameters reservationParameters, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/management-panel";
        }
        List<TableToReserve> tables = tableToReserveRepository.findAll();
        reservationParametersRepository.save(reservationParameters);
        StopWatch stopWatch = new StopWatch();
        for (TableToReserve table : tables) {
            reservationParametersService.addReservationsForPeriod(reservationParameters.getFirstDay(), reservationParameters.getLastDay(), reservationParameters.getMonToThuTimeFrom(), reservationParameters.getFriTimeFrom(), reservationParameters.getSatTimeFrom(), reservationParameters.getSunTimeFrom(), reservationParameters.getMonToThuTimeTo(), reservationParameters.getFriTimeTo(), reservationParameters.getSatTimeTo(), reservationParameters.getSunTimeTo(), reservationParameters.getDuration(), table);
        }
        logger.info("Execution time: " + stopWatch.getElapsedTime() + " ms");
        //with async - 5ms, without async 14562 ms!

        return "redirect:/admin/management-panel";
    }


}
