package com.example.diamondcastapp;

import java.util.ArrayList;

public class AppointmentList {
    private ArrayList<Appointment> appointmentList;

    public AppointmentList() {
        this.appointmentList = new ArrayList<>();
    }
    public AppointmentList(Appointment appointment) {
        this.appointmentList.add(appointment);
    }

    public ArrayList<Appointment> getAppointmentList() {
        return this.appointmentList;
    }
    public void setAppointmentList(ArrayList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }
}
