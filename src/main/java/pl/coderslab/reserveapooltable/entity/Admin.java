package pl.coderslab.reserveapooltable.entity;

import pl.coderslab.reserveapooltable.entity.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {


}
