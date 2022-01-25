package com.example.diamondcastapp;

import java.util.ArrayList;

public class Agent extends User {
    private String agencyOf;
    private String location;
    private double rating;
    private ArrayList<String> servicesOffered = new ArrayList<>();
    private String phone;

    public Agent() {  //default constructor using default User()
        super();
        this.agencyOf = "default";
        this.location = "default";
        this.rating = 100;
        this.servicesOffered.add("default");
        this.phone = "123-456-7890";
    }
    // default agent constructor with user already constructed
    public Agent(User user) {
        super(user.getUid(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getUserType());
        this.agencyOf = "default";
        this.location = "default";
        this.rating = 100;
        this.servicesOffered.add("default");
        this.phone = "123-456-7890";
    }
    // set all user and agent values with this constructor
    public Agent(String uid, String firstNameInput, String lastNameInput, String emailInput,
                 String usernameInput, UserType userTypeInput, String agencyOf, String location,
                 double rating, ArrayList<String> servicesOffered, String phone) {
        super(uid, firstNameInput,lastNameInput,emailInput, usernameInput, userTypeInput);
        this.agencyOf = agencyOf;
        this.location = location;
        this.rating = rating;
        this.servicesOffered = servicesOffered;
        this.phone = phone;
    }
    // constructor to set only agent values, use existing user.
    public Agent(User user, String agencyOf, String location,
                      double rating, ArrayList<String> servicesOffered, String phone) {
        super(user.getUid(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getUserType());
        this.agencyOf = agencyOf;
        this.location = location;
        this.rating = rating;
        this.servicesOffered = servicesOffered;
        this.phone = phone;
    }

    public String getAgencyOf() {
        return this.agencyOf;
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
    public String getPhone() {
        return this.phone;
    }
    public void setTypeOfContractor(String agencyOf) {
        this.agencyOf = agencyOf;
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
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
