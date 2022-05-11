package pl.coderslab.reserveapooltable.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    boolean isAvailable;

    @ManyToOne
    TableToReserve table;

    @ManyToOne
    User user;

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
}
