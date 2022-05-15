package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.reserveapooltable.entity.HolidayWorkdays;

public interface HolidayWorkdaysRepository extends JpaRepository<HolidayWorkdays, Long> {

}
