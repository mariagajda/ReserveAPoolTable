package pl.coderslab.reserveapooltable.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class ReservationsBasket {
    @Id
    @GeneratedValue
    private long id;
    private LocalDate date;
    private double priceSum;
    private String paymentMethod;
    private boolean isConfirmed;
    private boolean isPaid;

    @ManyToMany
    private List<Reservation> reservations;

    @ManyToOne
    private User user;

    public ReservationsBasket() {
    }

    public ReservationsBasket(LocalDate date, double priceSum, List<Reservation> reservations) {
        this.priceSum = priceSum;
        this.date = date;
        this.isConfirmed = false;
        this.reservations = reservations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(double priceSum) {
        this.priceSum = priceSum;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
