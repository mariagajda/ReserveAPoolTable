package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.reserveapooltable.entity.Reservation;
import pl.coderslab.reserveapooltable.entity.TableToReserve;

import java.util.List;


public interface TableToReserveRepository extends JpaRepository<TableToReserve, Long> {

}
