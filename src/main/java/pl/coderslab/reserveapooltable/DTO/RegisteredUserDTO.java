package pl.coderslab.reserveapooltable.DTO;


import pl.coderslab.reserveapooltable.validation.PasswordMatches;
import pl.coderslab.reserveapooltable.validation.ValidEmail;

import javax.validation.constraints.*;

@PasswordMatches
public class RegisteredUserDTO {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    private String username;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "\\d{9}")
    private String phoneNumber;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=(.*[a-z]){1,})(?=(.*[A-Z]){1,})(?=(.*[0-9]){1,})(?=(.*[!@#$%^&*()\\-__+.]){1,}).{8,}$")
    private String password;

    @NotNull
    @NotBlank
    private String matchingPassword;

    @AssertTrue
    private boolean usageAcceptance;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public boolean isUsageAcceptance() {
        return usageAcceptance;
    }

    public void setUsageAcceptance(boolean usageAcceptance) {
        this.usageAcceptance = usageAcceptance;
    }
}
