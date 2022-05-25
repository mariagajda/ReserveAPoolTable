package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.reserveapooltable.entity.RegisteredUser;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
    RegisteredUser findUserByEmail(String email);
    RegisteredUser findRegisteredUserByUsername(String username);
}
