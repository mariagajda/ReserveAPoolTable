package pl.coderslab.reserveapooltable.entity;

import pl.coderslab.reserveapooltable.validation.CustomValidEmail;

import javax.persistence.*;
import javax.persistence.GenerationType;
import javax.validation.constraints.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("unregistered")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public User() {
    }

    public User(String email, String phoneNumber, boolean usageAcceptance) {
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

}
