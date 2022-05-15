package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.reserveapooltable.entity.Price;

import java.time.LocalTime;

public interface PriceRepository extends JpaRepository<Price, Long> {

    Price findByDayOfWeekAndStartTime(String dayOfWeek, LocalTime startTime);
}
