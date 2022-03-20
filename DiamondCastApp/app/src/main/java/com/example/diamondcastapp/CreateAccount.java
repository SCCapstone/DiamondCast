package com.example.diamondcastapp;

import com.google.android.material.snackbar.Snackbar;

public class CreateAccount {
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
}

