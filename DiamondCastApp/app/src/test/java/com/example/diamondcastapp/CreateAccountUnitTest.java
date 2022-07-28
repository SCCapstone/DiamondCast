package com.example.diamondcastapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CreateAccountUnitTest {
    private CreateAccount test = new CreateAccount();

    @Test
    public void checkPasswordRequirementsMetTest_withBadPassword() {
        String password = "bad";
        boolean check = test.checkPasswordRequirementsMet(password);
        assertEquals(false,check);
    }
    @Test
    public void checkPasswordIsEqualToConfirmation_withBadConfirm() {
        String password = "password";
        String confirm = "passw0rd";
        boolean check = test.checkPasswordIsEqualToConfirmation(password, confirm);
        assertEquals(false,check);
    }

    @Test
    public void checkPasswordIsEqualToConfirm() {
        String password = "password";
        String confirm = "password";
        boolean check = test.checkPasswordIsEqualToConfirmation(password, confirm);
        assertEquals(true, check);
    }

    @Test
    public void checkValidEmail_withBadConfirm() {
        String email = "notAValidEmail";
        boolean check = test.checkValidEmail(email);
        assertEquals(false,check);
    }
}
