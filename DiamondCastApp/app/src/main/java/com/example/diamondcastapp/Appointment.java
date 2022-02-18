package com.example.diamondcastapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;

public class Appointment {

    public String title;
    public String date; //not using Date because deprecated, simpleDateFormat wont map to database
    public String service;
    public boolean active;

    public Appointment() {

        this.title = "Appointment with:";
        this.date = "";
        this.service = "service";
        this.active = true;
    }

    public Appointment( String title, String date, String service, Boolean active) {

        this.title = title;
        this.date = date;
        this.service = service;
        this.active = active;
    }


    public String getAppointmentTitle() {
        return this.title;
    }
    public String getAppointmentDate() {
        return this.date;
    }
    public String getAppointmentService() {
        return this.service;
    }
    public boolean getAppointmentActive() {
        return this.active;
    }
    public void setAppointmentTitle(String title) {
        this.title = title;
    }
    public void setAppointmentDate(String date) {
        this.date = date;
    }
    public void setAppointmentService(String service) {
        this.service = service;
    }
    public void setAppointmentActive(boolean isActive) {
        this.active = isActive;
    }
}

