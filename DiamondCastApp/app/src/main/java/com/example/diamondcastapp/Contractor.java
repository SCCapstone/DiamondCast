package com.example.diamondcastapp;

import java.util.ArrayList;

public class Contractor extends User{
    private String typeOfContractor;
    private String location;
    private double rating;
    private ArrayList<String> servicesOffered = new ArrayList<>();
    private ArrayList<String> paymentMethods = new ArrayList<>();

    public Contractor() {
        this.typeOfContractor = "default";
        this.location = "default";
        this.rating = 100;
        servicesOffered.add("default");
        paymentMethods.add("default");

    }
}
