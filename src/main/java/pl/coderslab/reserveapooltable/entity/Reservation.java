package pl.coderslab.reserveapooltable.entity;

import pl.coderslab.reserveapooltable.enums.PriceGroup;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Future
    private LocalDate date;
    @Future
    private LocalDateTime startDateTime;
    @Future
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

    public Reservation(LocalDate date, LocalDateTime startDateTime, LocalDateTime endDateTime, TableToReserve table) {
        this.date = date;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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
                ", user name=" + user.getEmail() +
                '}';
    }
}
