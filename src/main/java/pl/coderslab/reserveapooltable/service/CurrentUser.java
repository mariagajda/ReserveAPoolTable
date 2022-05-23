package pl.coderslab.reserveapooltable.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {
    private final pl.coderslab.reserveapooltable.entity.RegisteredUser registeredUser;
    public CurrentUser(String username, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.coderslab.reserveapooltable.entity.RegisteredUser registeredUser) {
        super(username, password, authorities);
        this.registeredUser = registeredUser;
    }
    public pl.coderslab.reserveapooltable.entity.RegisteredUser getUser() {return registeredUser;}
}