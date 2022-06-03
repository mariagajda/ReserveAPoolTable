package pl.coderslab.reserveapooltable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.coderslab.reserveapooltable.entity.RegisteredUser;

import java.util.HashSet;
import java.util.Set;

public class SpringDataUserDetailsService implements UserDetailsService {
    private RegisteredUserService registeredUserService;

    @Autowired
    public void setRegisterUserRepository(RegisteredUserService registerUserService) {
        this.registeredUserService = registerUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        RegisteredUser user = registeredUserService.findRegisteredUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(r ->
                grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));
        return new CurrentUser(
                user.getUsername(), user.getPassword(), grantedAuthorities, user);
    }
}
