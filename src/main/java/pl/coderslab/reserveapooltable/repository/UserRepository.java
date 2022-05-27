package pl.coderslab.reserveapooltable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.reserveapooltable.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
