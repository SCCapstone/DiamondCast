package com.example.diamondcastapp;

import java.util.ArrayList;

public class AppointmentList {
    private ArrayList<Appointment> appointmentList;

    //different constructors that may be needed
    public AppointmentList() {
        this.appointmentList = new ArrayList<>();
    }

    public AppointmentList(Appointment appointment) {
        this.appointmentList.add(appointment);
    }

    public AppointmentList(ArrayList<Appointment> appointments) { this.appointmentList = appointments; }

    //getters
    public ArrayList<Appointment> getAppointmentList() {
        return this.appointmentList;
    }

    //setters
    public void setAppointmentList(ArrayList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    //add and remove appointment (regular arrayList methods may work but not sure)

    public void addAppointment(Appointment appointment) {
        this.appointmentList.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        this.appointmentList.remove(appointment);
    }
}
