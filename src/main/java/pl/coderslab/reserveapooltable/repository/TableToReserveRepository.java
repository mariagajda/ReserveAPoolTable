package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.reserveapooltable.entity.TableToReserve;

@Repository
public interface TableToReserveRepository extends JpaRepository<TableToReserve, Long> {

}
