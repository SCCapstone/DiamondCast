package com.example.diamondcastapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;

public class Appointment {
    public String id;
    public String title;
    public SimpleDateFormat date;
    public String service;
    public boolean active;

    public Appointment() {
        this.id = "id";
        this.title = title;
        this.date = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        this.service = "service";
        this.active = true;
         <string name="confirm_your_appointment">Confirm your appointment</string>
    <string name="confirm_appointment">Confirm Appointment</string>
    <string name="date_format_confirm">dd/MM/yy</string>
    <string name="default_display_appointment_with">appointment with this person</string>
    <string name="default_time_confirm">12:00</string>
    }

    public Appointment(String uid, String title, SimpleDateFormat date, String service, Boolean active) {
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
    public SimpleDateFormat getAppointmentDate() {
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
    public void setAppointmentDate(SimpleDateFormat date) {
        this.date = date;
    }
    public void setAppointmentService(String service) {
        this.service = service;
    }
    public void setAppointmentActive(boolean isActive) {
        this.active = isActive;
    }
}
