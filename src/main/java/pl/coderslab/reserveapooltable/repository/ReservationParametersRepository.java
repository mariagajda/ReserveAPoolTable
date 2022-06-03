package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.reserveapooltable.entity.ReservationParameters;

@Repository
public interface ReservationParametersRepository extends JpaRepository<ReservationParameters, Long> {

}
