package pl.coderslab.reserveapooltable.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("registered")
public class RegisteredUser extends User{

    String password;
    double discount;
    int reservationsCounter;

    public RegisteredUser() {
        discount = 0.0;
        reservationsCounter = 0;
    }

    public RegisteredUser(String name, String email, String phoneNumber, String password) {
        super(name, email, phoneNumber);
        this.password = password;
        discount = 0.0;
        reservationsCounter = 0;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getReservationsCounter() {
        return reservationsCounter;
    }

    public void setReservationsCounter(int reservationsCounter) {
        this.reservationsCounter = reservationsCounter;
    }
}
