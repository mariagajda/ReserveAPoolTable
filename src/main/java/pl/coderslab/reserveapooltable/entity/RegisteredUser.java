package pl.coderslab.reserveapooltable.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("registered")
public class RegisteredUser extends User {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=(.*[a-z]){1,})(?=(.*[A-Z]){1,})(?=(.*[0-9]){1,})(?=(.*[!@#$%^&*()\\-__+.]){1,}).{8,}$", message = "{pattern.password}")
    private String password;
    private Double discount;
    private int enabled;
    private Integer reservationsCounter;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany
    private List<ReservationsBasket> reservationsHistory;

    public RegisteredUser() {
        discount = 0.0;
        reservationsCounter = 0;
    }

    public RegisteredUser(String username, String email, String phoneNumber, boolean usageAcceptance, String password) {
        super(username, email, phoneNumber, usageAcceptance);
        this.password = password;
        discount = 0.0;
        reservationsCounter = 0;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setReservationsCounter(Integer reservationsCounter) {
        this.reservationsCounter = reservationsCounter;
    }

    public List<ReservationsBasket> getReservationsHistory() {
        return reservationsHistory;
    }

    public void setReservationsHistory(List<ReservationsBasket> reservationsHistory) {
        this.reservationsHistory = reservationsHistory;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}
