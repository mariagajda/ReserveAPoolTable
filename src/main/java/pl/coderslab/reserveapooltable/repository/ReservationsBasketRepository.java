package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.reserveapooltable.entity.ReservationsBasket;

@Repository
public interface ReservationsBasketRepository extends JpaRepository<ReservationsBasket, Long> {

}
