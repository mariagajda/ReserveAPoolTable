package pl.coderslab.reserveapooltable.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.reserveapooltable.entity.Reservation;
import java.time.LocalDate;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.date = :date ORDER BY r.table.tableNumber, r.startDateTime")
    List<Reservation> findAllByDateSorted(@Param("date") LocalDate date);

    @Query("SELECT r FROM Reservation r ORDER BY r.date DESC")
    List<Reservation> findAllSortByReservationDateDesc();





}