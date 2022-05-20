package pl.coderslab.reserveapooltable.entity;

import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.reserveapooltable.enums.PriceGroup;
import pl.coderslab.reserveapooltable.repository.HolidayWorkdaysRepository;
import pl.coderslab.reserveapooltable.repository.PriceRepository;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private PriceGroup priceGroup;
    private boolean isAvailable;
    private double pricePerReservation;


    @ManyToOne
    private TableToReserve table;

    @ManyToOne
    private User user;

    public Reservation() {
    }

    public Reservation(LocalDate date, LocalDateTime startDateTime, LocalDateTime endDateTime, PriceGroup priceGroup, TableToReserve table) {
        this.date = date;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.priceGroup = priceGroup;
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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public PriceGroup getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(PriceGroup priceGroup) {
        this.priceGroup = priceGroup;
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

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date=" + date +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", priceGroup=" + priceGroup +
                ", isAvailable=" + isAvailable +
                ", pricePerReservation=" + pricePerReservation +
                ", table no.=" + table.getTableNumber() +
                ", user name=" + user.getName() +
                '}';
    }
}
