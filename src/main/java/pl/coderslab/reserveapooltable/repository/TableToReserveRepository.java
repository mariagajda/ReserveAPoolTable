package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.reserveapooltable.entity.TableToReserve;


public interface TableToReserveRepository extends JpaRepository<TableToReserve, Long> {

}
