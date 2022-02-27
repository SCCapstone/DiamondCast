package com.example.diamondcastapp;

import java.util.ArrayList;

public class Contractor extends User{
    private String typeOfContractor;
    private String location;
    private double rating;
    private ArrayList<String> servicesOffered = new ArrayList<>();
    private ArrayList<String> paymentMethods = new ArrayList<>();

    public Contractor() { //default constructor using default User()
        super();
        this.typeOfContractor = "default";
        this.location = "default";
        this.rating = 100;
        this.servicesOffered.add("default");
        this.paymentMethods.add("default");
    }

    public Contractor(User user) { //default constructor but uses basic client details;
        super( user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getUserType());
        this.typeOfContractor = "default";
        this.location = "default";
        this.rating = 100;
        this.servicesOffered.add("default");
        this.paymentMethods.add("default");
    }
    // constructor to set base user and contractor values at one time
    public Contractor(String uid, String firstNameInput, String lastNameInput, String emailInput,
                      String usernameInput, UserType userTypeInput, String typeOfContractor, String location,
                      double rating, ArrayList<String> servicesOffered, ArrayList<String> paymentMethods) {
        super(firstNameInput,lastNameInput,emailInput, usernameInput, userTypeInput);
        this.typeOfContractor = typeOfContractor;
        this.location = location;
        this.rating = rating;
        this.servicesOffered = servicesOffered;
        this.paymentMethods = paymentMethods;
    }
    // constructor to set contractor details when already have base user details
    public Contractor(User user, String typeOfContractor, String location,
                      double rating, ArrayList<String> servicesOffered, ArrayList<String> paymentMethods) {
        super(user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getUserType());
        this.typeOfContractor = typeOfContractor;
        this.location = location;
        this.rating = rating;
        this.servicesOffered = servicesOffered;
        this.paymentMethods = paymentMethods;
    }

    public String getTypeOfContractor() {
        return this.typeOfContractor;
    }
    public String getLocation() {
        return this.location;
    }
    public double getRating() {
        return this.rating;
    }
    public ArrayList<String> getServicesOffered() {
        return this.servicesOffered;
    }
    public ArrayList<String> getPaymentMethods() {
        return this.paymentMethods;
    }

    public void setTypeOfContractor(String typeOfContractor) {
        this.typeOfContractor = typeOfContractor;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setServicesOffered(ArrayList<String> servicesOffered) {
        this.servicesOffered = servicesOffered;
    }
    public void setPaymentMethods(ArrayList<String> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}

