package pl.coderslab.reserveapooltable.validation;

import pl.coderslab.reserveapooltable.DTO.RegisteredUserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        RegisteredUserDTO registeredUser = (RegisteredUserDTO) o;
        return registeredUser.getPassword().equals(registeredUser.getMatchingPassword());
    }
}
