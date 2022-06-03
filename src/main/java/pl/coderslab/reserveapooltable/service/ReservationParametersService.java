package pl.coderslab.reserveapooltable.service;

import org.springframework.stereotype.Service;
import pl.coderslab.reserveapooltable.entity.HolidayWorkday;
import pl.coderslab.reserveapooltable.entity.Reservation;
import pl.coderslab.reserveapooltable.entity.TableToReserve;
import pl.coderslab.reserveapooltable.enums.PriceGroup;
import pl.coderslab.reserveapooltable.repository.HolidayWorkdaysRepository;
import pl.coderslab.reserveapooltable.repository.PriceRepository;
import pl.coderslab.reserveapooltable.repository.ReservationRepository;
import pl.coderslab.reserveapooltable.repository.TableToReserveRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationParametersService {
    private final PriceRepository priceRepository;
    private final HolidayWorkdaysRepository holidayWorkdaysRepository;
    private final ReservationRepository reservationRepository;
    private final TableToReserveRepository tableToReserveRepository;

    public ReservationParametersService(PriceRepository priceRepository, HolidayWorkdaysRepository holidayWorkdaysRepository, ReservationRepository reservationRepository, TableToReserveRepository tableToReserveRepository) {
        this.priceRepository = priceRepository;
        this.holidayWorkdaysRepository = holidayWorkdaysRepository;
        this.reservationRepository = reservationRepository;
        this.tableToReserveRepository = tableToReserveRepository;
    }

    public void addReservationsForPeriod(LocalDate firstDay, LocalDate lastDay, LocalTime monToThuTimeFrom, LocalTime friTimeFrom, LocalTime satTimeFrom, LocalTime sunTimeFrom, LocalTime monToThuTimeTo, LocalTime friTimeTo, LocalTime satTimeTo, LocalTime sunTimeTo, long duration) {
        List<LocalDate> datesBetweenFirstAndLastDay = new ArrayList<>();
        for (int i = 0; i <= firstDay.until(lastDay, ChronoUnit.DAYS); i++) {
            datesBetweenFirstAndLastDay.add(firstDay.plusDays(i));
        }
        List<TableToReserve> tables = tableToReserveRepository.findAll();
        datesBetweenFirstAndLastDay.forEach(date -> {
            if (date.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                setReservationParameters(friTimeTo, friTimeFrom, tables, duration, date);
            } else if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                setReservationParameters(satTimeTo, satTimeFrom, tables, duration, date);
            } else if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || holidayWorkdaysRepository.findAll().stream().map(HolidayWorkday::getDate).collect(Collectors.toList()).contains(date)) {
                setReservationParameters(sunTimeTo, sunTimeFrom, tables, duration, date);
            } else {
                setReservationParameters(monToThuTimeTo, monToThuTimeFrom, tables, duration, date);
            }
        });
    }

    public void countPrice(Reservation reservation, long duration) {
        if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.FRI, false).getPricePerHour()) / (60.0 / duration));
            } else {
                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.FRI, true).getPricePerHour() / (60.0 / duration));
            }
        } else if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SAT, false).getPricePerHour()) / (60.0 / duration));
            } else {
                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SAT, true).getPricePerHour() / (60.0 / duration));
            }
        } else if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY) || holidayWorkdaysRepository.findAll().stream().map(HolidayWorkday::getDate).collect(Collectors.toList()).contains(reservation.getDate())) {
            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SUNandHOLIDAYS, false).getPricePerHour()) / (60.0 / duration));
            } else {
                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SUNandHOLIDAYS, true).getPricePerHour() / (60.0 / duration));
            }
        } else {
            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.MONtoTHU, false).getPricePerHour()) / (60.0 / duration));
            } else {
                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.MONtoTHU, true).getPricePerHour() / (60.0 / duration));
            }
        }
    }

    public void setReservationParameters(LocalTime timeTo, LocalTime timeFrom, List<TableToReserve> tables, long duration, LocalDate date) {
        LocalDateTime dateTimeFrom;
        LocalDateTime dateTimeTo;
        if (timeTo.getHour() > timeFrom.getHour()) {
            dateTimeFrom = LocalDateTime.of(date, timeFrom);
            dateTimeTo = LocalDateTime.of(date, timeTo);
        } else {
            dateTimeFrom = LocalDateTime.of(date, timeFrom);
            dateTimeTo = LocalDateTime.of(date.plusDays(1), timeTo);
        }
        long numberOfHours = dateTimeFrom.until(dateTimeTo, ChronoUnit.HOURS);
        long numberOfReservationsPerDay = numberOfHours * 60 / duration;

        for (int i = 0; i < numberOfReservationsPerDay; i++) {

            for (TableToReserve table : tables) {
                Reservation reservation = new Reservation(date, dateTimeFrom, dateTimeFrom.plusMinutes(duration), PriceGroup.FRI, table);
                countPrice(reservation, duration);
                reservationRepository.save(reservation);
            }
            dateTimeFrom = dateTimeFrom.plusMinutes(duration);
        }
    }
}
