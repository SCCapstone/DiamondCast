package com.example.diamondcastapp;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

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
}
