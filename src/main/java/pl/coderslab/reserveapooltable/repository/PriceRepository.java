package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.reserveapooltable.entity.Price;
import pl.coderslab.reserveapooltable.enums.PriceGroup;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    Price findByPriceGroupAndIsNightTime(PriceGroup priceGroup, boolean isNightTime);
}
