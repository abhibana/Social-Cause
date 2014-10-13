package com.practice.compass.Validators;

public class PasswordValidator implements CommonValidator {
    @Override
    public final boolean isInvalid(String text) {
        return text.length() < 6;
    }
}
