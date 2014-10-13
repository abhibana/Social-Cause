package com.practice.compass.Validators;

public class ContactNumberValidator implements CommonValidator {
    public static final String MOBILE_NUMBER_REGEX = "^([0-9]+)$";

    @Override
    public boolean isInvalid(String text) {
        return !text.matches(MOBILE_NUMBER_REGEX);
    }
}
