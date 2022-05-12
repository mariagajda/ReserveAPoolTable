package pl.coderslab.reserveapooltable.entity;

import pl.coderslab.reserveapooltable.entity.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

    static double mondayToThursdayBefore18PricePerHour = 18.0;
    static double mondayToThursdayAfter18PricePerHour = 23.0;
    static double fridayBefore18PricePerHour = 18.0;
    static double fridayAfter18PricePerHour = 30.0;
    static double saturdayBefore18PricePerHour = 23.0;
    static double saturdayAfter18PricePerHour = 30.0;
    static double sundayAndHolidaysBefore18PricePerHour = 23.0;
    static double sundayAndHolidaysAfter18PricePerHour = 23.0;

    static List<LocalDate> holidayDatesWorkdays = new ArrayList<>();
    static List<LocalDate> datesClosed = new ArrayList<>();



}
