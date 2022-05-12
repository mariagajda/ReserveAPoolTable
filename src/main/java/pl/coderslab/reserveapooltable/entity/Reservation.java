package pl.coderslab.reserveapooltable.entity;

import javax.persistence.*;
import java.time.DayOfWeek;
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
    double price;

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
        countPrice();
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void countPrice(){
        if(date.getDayOfWeek().equals(DayOfWeek.FRIDAY) && startTime.isBefore(LocalTime.of(18, 00))){
            setPrice(Admin.fridayBefore18PricePerHour/2);
        }
        else if(date.getDayOfWeek().equals(DayOfWeek.FRIDAY) && startTime.isAfter(LocalTime.of(17, 59))){
            setPrice(Admin.fridayAfter18PricePerHour/2);
        }
        else if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY) && startTime.isBefore(LocalTime.of(18, 00))){
            setPrice(Admin.saturdayBefore18PricePerHour/2);
        }
        else if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY) && startTime.isAfter(LocalTime.of(17, 59))){
            setPrice(Admin.saturdayAfter18PricePerHour/2);
        }
        else if(date.getDayOfWeek().equals(DayOfWeek.SUNDAY) && startTime.isBefore(LocalTime.of(18, 00))){
            setPrice(Admin.sundayAndHolidaysBefore18PricePerHour/2);
        }
        else if(date.getDayOfWeek().equals(DayOfWeek.SUNDAY) && startTime.isAfter(LocalTime.of(17, 59))){
            setPrice(Admin.sundayAndHolidaysAfter18PricePerHour/2);
        }
        else if(Admin.holidayDatesWorkdays.contains(date) && startTime.isBefore(LocalTime.of(18, 00))){
            setPrice(Admin.sundayAndHolidaysBefore18PricePerHour/2);
        }
        else if(Admin.holidayDatesWorkdays.contains(date) && startTime.isAfter(LocalTime.of(17, 59))){
            setPrice(Admin.sundayAndHolidaysAfter18PricePerHour/2);
        }
        else if(startTime.isBefore(LocalTime.of(18, 00))){
            setPrice(Admin.mondayToThursdayBefore18PricePerHour);
        }
        else{
            setPrice(Admin.mondayToThursdayAfter18PricePerHour);
        }
    }
}
