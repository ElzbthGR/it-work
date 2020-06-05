package ru.kpfu.itis.services.validation.annotations;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.services.UsersService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {

    private final UsersService usersService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || !usersService.existsByEmail(value);
    }
}
