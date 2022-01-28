package com.example.diamondcastapp;

import java.util.ArrayList;

public class Client extends User {
    private String uid;
    private String location;
    private String phone;

    public Client() {  //default constructor using default User()
        super();
        this.location = "default";
        this.phone = "123-456-7890";
    }
    // default agent constructor with user already constructed
    public Client(User user) {
        super(user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getUserType());
        this.location = "default";
        this.phone = "123-456-7890";
    }
    // set all user and agent values with this constructor
    public Client(String uid, String firstNameInput, String lastNameInput, String emailInput,
                 String usernameInput, UserType userTypeInput, String location, ArrayList<String> servicesOffered, String phone) {
        super(firstNameInput,lastNameInput,emailInput, usernameInput, userTypeInput);
        this.location = location;
        this.phone = phone;
    }
    // constructor to set only agent values, use existing user.
    public Client(User user, String location, String phone) {
        super(user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getUserType());
        this.location = location;
        this.phone = phone;
    }



    public String getLocation() {
        return this.location;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
