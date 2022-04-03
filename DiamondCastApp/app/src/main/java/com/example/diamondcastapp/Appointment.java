package com.example.diamondcastapp;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;

public class Appointment implements Serializable {

    public String title;
    public String date; //not using Date because deprecated, simpleDateFormat wont map to database
    public String time;
    public String appointmentOwnerId;
    public String appointmentWithId;
    public ArrayList<String> services;
    public boolean active;

    public Appointment() {

        this.title = "Appointment with:";
        this.date = "00-00-0000";
        this.time = "0:00";
        this.services = new ArrayList<>();
        this.active = true;
    }

    public Appointment( String title, String date, String time, String appointmentOwnerId, String appointmentWithId, ArrayList<String> services, Boolean active) {

        this.title = title;
        this.date = date;
        this.time = time;
        this.appointmentOwnerId = appointmentOwnerId;
        this.appointmentWithId = appointmentWithId;
        this.services = services;
        this.active = active;
    }


    public String getTitle() {
        return this.title;
    }
    public String getDate() {
        return this.date;
    }
    public String getTime() { return this.time; }
    public String getAppointmentOwnerId() { return this.appointmentOwnerId; }
    public String getAppointmentWithId() { return this.appointmentWithId; }
    public ArrayList<String> getServices() {
        return this.services;
    }
    public boolean getActive() {
        return this.active;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setAppointmentOwnerId(String id) { this.appointmentOwnerId = id; }
    public void setAppointmentWithId(String id) { this.appointmentWithId = id; }
    public void setService(ArrayList<String> services) {
        this.services = services;
    }
    public void setActive(boolean isActive) {
        this.active = isActive;
    }
}

