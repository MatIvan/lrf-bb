package ru.mativ.lrfbb.data.dto;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto user = (UserDto) target;

        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            errors.rejectValue("login", "value.empty");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            errors.rejectValue("name", "value.empty");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            errors.rejectValue("password", "value.empty");
        }

        //fileds "id" and "roles" may be empty
    }

    public static boolean validPassword(UserDto target) {
        if (target == null) {
            return false;
        }

        final String pass = target.getPassword();
        if (pass == null || pass.isEmpty()) {
            return false;
        }

        // TODO check password validation

        return true;
    }

}
