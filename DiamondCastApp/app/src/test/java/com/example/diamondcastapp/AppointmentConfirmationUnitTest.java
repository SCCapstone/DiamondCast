package com.example.diamondcastapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppointmentConfirmationUnitTest {
    private AppointmentConfirmation test = new AppointmentConfirmation();


    @Test
    public void checkGetMonthFormatCorrect() {
        int month = 4;
        assertEquals(true,test.getMonthFormat(month).equals("APR"));
    }
    @Test
    public void checkGetMonthFormatInvalid() {
        int month = 0;
        assertEquals(true,test.getMonthFormat(month).equals("INV"));
    }
}
