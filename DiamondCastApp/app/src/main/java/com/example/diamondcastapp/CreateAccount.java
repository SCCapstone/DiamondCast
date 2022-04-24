package com.example.diamondcastapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//used to abstract methods used in create account to make testable without mocking
public class CreateAccount {
    private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final int PASSWORD_MIN = 6;
    private static final int PASSWORD_MAX = 30;

    public CreateAccount() {

    }

    //Determines if password requirements are met
    // Password Requirements
    //   - length is (6-30) characters
    //   - more to come? (needs at least 1 number 1 uppercase)
    // **an empty password will fail check**
    public boolean checkPasswordRequirementsMet(String password) {
        if (password.length() < PASSWORD_MIN || password.length() > PASSWORD_MAX) {
            return false;
        }
        return true;
    }

    //returns true if password and confirm password are equal
    public boolean checkPasswordIsEqualToConfirmation(String password, String confirmPassword){
        if (!(password.equals(confirmPassword))) {
            return false;
        }
        return true;
    }

    //returns true if the input string would make a valid email
    public boolean checkValidEmail(String email){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }
}

