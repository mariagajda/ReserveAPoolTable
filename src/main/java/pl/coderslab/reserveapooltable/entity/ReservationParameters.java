package pl.coderslab.reserveapooltable.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class ReservationParameters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate firstDay;
    private LocalDate lastDay;
    private LocalTime monToThuTimeFrom;
    private LocalTime friTimeFrom;
    private LocalTime satTimeFrom;
    private LocalTime sunTimeFrom;
    private LocalTime monToThuTimeTo;
    private LocalTime friTimeTo;
    private LocalTime satTimeTo;
    private LocalTime sunTimeTo;
    private long duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(LocalDate firstDay) {
        this.firstDay = firstDay;
    }

    public LocalDate getLastDay() {
        return lastDay;
    }

    public void setLastDay(LocalDate lastDay) {
        this.lastDay = lastDay;
    }

    public LocalTime getMonToThuTimeFrom() {
        return monToThuTimeFrom;
    }

    public void setMonToThuTimeFrom(LocalTime monToThuTimeFrom) {
        this.monToThuTimeFrom = monToThuTimeFrom;
    }

    public LocalTime getFriTimeFrom() {
        return friTimeFrom;
    }

    public void setFriTimeFrom(LocalTime friTimeFrom) {
        this.friTimeFrom = friTimeFrom;
    }

    public LocalTime getSatTimeFrom() {
        return satTimeFrom;
    }

    public void setSatTimeFrom(LocalTime satTimeFrom) {
        this.satTimeFrom = satTimeFrom;
    }

    public LocalTime getSunTimeFrom() {
        return sunTimeFrom;
    }

    public void setSunTimeFrom(LocalTime sunTimeFrom) {
        this.sunTimeFrom = sunTimeFrom;
    }

    public LocalTime getMonToThuTimeTo() {
        return monToThuTimeTo;
    }

    public void setMonToThuTimeTo(LocalTime monToThuTimeTo) {
        this.monToThuTimeTo = monToThuTimeTo;
    }

    public LocalTime getFriTimeTo() {
        return friTimeTo;
    }

    public void setFriTimeTo(LocalTime friTimeTo) {
        this.friTimeTo = friTimeTo;
    }

    public LocalTime getSatTimeTo() {
        return satTimeTo;
    }

    public void setSatTimeTo(LocalTime satTimeTo) {
        this.satTimeTo = satTimeTo;
    }

    public LocalTime getSunTimeTo() {
        return sunTimeTo;
    }

    public void setSunTimeTo(LocalTime sunTimeTo) {
        this.sunTimeTo = sunTimeTo;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
