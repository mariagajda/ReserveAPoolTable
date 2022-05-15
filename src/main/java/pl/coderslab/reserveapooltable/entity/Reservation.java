package pl.coderslab.reserveapooltable.entity;

import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.reserveapooltable.repository.HolidayWorkdaysRepository;
import pl.coderslab.reserveapooltable.repository.PriceRepository;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;
    private double pricePerReservation;

    @ManyToOne
    private TableToReserve table;

    @ManyToOne
    private  User user;

    public Reservation() {
    }

    public Reservation(LocalDate date, LocalTime startTime, LocalTime endTime, TableToReserve table) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        isAvailable = true;
        this.table = table;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public TableToReserve getTable() {
        return table;
    }

    public void setTable(TableToReserve tableToReserve) {
        this.table = tableToReserve;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPricePerReservation() {
        return pricePerReservation;
    }

    public void setPricePerReservation(double price) {
        this.pricePerReservation = price;
    }


}
