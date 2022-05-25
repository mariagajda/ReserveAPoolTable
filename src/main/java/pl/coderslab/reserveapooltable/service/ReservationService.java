//package pl.coderslab.reserveapooltable.service;
//
//import org.springframework.stereotype.Service;
//import pl.coderslab.reserveapooltable.entity.HolidayWorkday;
//import pl.coderslab.reserveapooltable.entity.Reservation;
//import pl.coderslab.reserveapooltable.enums.PriceGroup;
//import pl.coderslab.reserveapooltable.repository.HolidayWorkdaysRepository;
//import pl.coderslab.reserveapooltable.repository.PriceRepository;
//
//import java.time.DayOfWeek;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.stream.Collectors;
//
//@Service
//public class ReservationService {
//
//private final PriceRepository priceRepository;
//private final HolidayWorkdaysRepository holidayWorkdaysRepository;
//
//
//    public ReservationService(PriceRepository priceRepository, HolidayWorkdaysRepository holidayWorkdaysRepository, ReservationService reservationService) {
//        this.priceRepository = priceRepository;
//        this.holidayWorkdaysRepository = holidayWorkdaysRepository;
//    }
//
//    public void countPrice(Reservation reservation, long duration) {
//        if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
//            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
//                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.FRI, false).getPricePerHour()) / (60/duration));
//            } else {
//                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.FRI, true).getPricePerHour() / (60/duration));
//            }
//        } else if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
//            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
//                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SAT, false).getPricePerHour()) / (60/duration));
//            } else {
//                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SAT, true).getPricePerHour() / (60/duration));
//            }
//        } else if (reservation.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY) || holidayWorkdaysRepository.findAll().stream().map(HolidayWorkday::getDate).collect(Collectors.toList()).contains(reservation.getDate())) {
//            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
//                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SUNandHOLIDAYS, false).getPricePerHour()) / (60/duration));
//            } else {
//                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.SUNandHOLIDAYS, true).getPricePerHour() / (60/duration));
//            }
//        } else {
//            if (reservation.getStartDateTime().isBefore(LocalDateTime.of(reservation.getDate(), LocalTime.of(18, 0)))) {
//                reservation.setPricePerReservation((priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.MONtoTHU, false).getPricePerHour()) / (60/duration));
//            } else {
//                reservation.setPricePerReservation(priceRepository.findByPriceGroupAndIsNightTime(PriceGroup.MONtoTHU, true).getPricePerHour() / (60/duration));
//            }
//        }
//    }
//}
