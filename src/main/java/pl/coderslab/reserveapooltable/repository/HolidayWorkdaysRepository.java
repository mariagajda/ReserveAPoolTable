package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.reserveapooltable.entity.HolidayWorkday;

public interface HolidayWorkdaysRepository extends JpaRepository<HolidayWorkday, Long> {

}
