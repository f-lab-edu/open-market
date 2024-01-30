package com.flab.openmarket.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class EnumValidator implements ConstraintValidator<Enum, String> {
    private Enum enumAnnotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.enumAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Object [] enumValues = this.enumAnnotation.enumClass().getEnumConstants();

        if(enumValues == null || Boolean.FALSE.equals(StringUtils.hasText(value))) {
            return false;
        }

        for(Object enumValue : enumValues) {
            String enumValueStr = enumValue.toString();
            boolean isSameValue = this.enumAnnotation.ignoreCase() ?
                    value.equalsIgnoreCase(enumValueStr) : value.equals(enumValueStr);

            if(isSameValue) {
                return true;
            }
        }

        return false;
    }
}
