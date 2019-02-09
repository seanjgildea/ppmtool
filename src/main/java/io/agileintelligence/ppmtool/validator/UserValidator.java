package io.agileintelligence.ppmtool.validator;

import io.agileintelligence.ppmtool.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {

    private int pwLength = 6;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        User user = (User)object;

        if (user.getPassword() != null && user.getPassword().length() < pwLength ) {
            errors.rejectValue("password", "Length", "Password must be at least " + pwLength + " characters");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("password", "Match", "Passwords must match");
        }

    }
}
