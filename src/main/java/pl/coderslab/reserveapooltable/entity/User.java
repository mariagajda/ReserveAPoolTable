package pl.coderslab.reserveapooltable.entity;

import pl.coderslab.reserveapooltable.validation.CustomValidEmail;

import javax.persistence.*;
import javax.persistence.GenerationType;
import javax.validation.constraints.*;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("unregistered")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    private String username;

    @NotNull
    @NotBlank
    @CustomValidEmail
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(?<!\\w)(\\(?(\\+|00)?48\\)?)?[ -]?\\d{3}[ -]?\\d{3}[ -]?\\d{3}(?!\\w)", message = "{pattern.phonenumber}")
    private String phoneNumber;

    @AssertTrue
    private boolean usageAcceptance;

    @OneToMany
    private List<Reservation> reservations;

    public User() {
    }

    public User(String username, String email, String phoneNumber, boolean usageAcceptance) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.usageAcceptance = usageAcceptance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isUsageAcceptance() {
        return usageAcceptance;
    }

    public void setUsageAcceptance(boolean usageAcceptance) {
        this.usageAcceptance = usageAcceptance;
    }


    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
