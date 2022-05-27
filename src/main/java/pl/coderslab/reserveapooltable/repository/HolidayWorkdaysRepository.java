package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.reserveapooltable.entity.HolidayWorkday;

@Repository
public interface HolidayWorkdaysRepository extends JpaRepository<HolidayWorkday, Long> {

}
