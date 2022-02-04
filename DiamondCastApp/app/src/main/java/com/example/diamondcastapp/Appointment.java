package com.example.diamondcastapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;

public class Appointment {
    public String id;
    public String title;
    public String date;
    public String service;
    public boolean active;

    public Appointment() {
        this.id = "id";
        this.title = title;
        this.date = "";
        this.service = "service";
        this.active = true;
    }

    public Appointment(String uid, String title, String date, String service, Boolean active) {
        this.id = uid;
        this.title = title;
        this.date = date;
        this.service = service;
        this.active = active;
    }

    public String getAppointmentID() {
        return this.id;
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
    public void setAppointmentID(String id) {
        this.id = id;
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
