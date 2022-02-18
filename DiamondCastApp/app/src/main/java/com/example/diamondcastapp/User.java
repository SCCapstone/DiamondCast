package com.example.diamondcastapp;

/**************************************************************
    *User Class for CSCE490 Project 
    *@author Daniel McKenna & Frank 
    *@version 1.0.0 11-28-21
    *Copyright tbd
***************************************************************/
/* Class for a user in the app 
    this class contains a a default and a normal constructor
     and getters and setters for all instance variables */

    public class User {

        //instance variables

        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private UserType userType;

    public User () {

        this.username = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.userType = UserType.Client;
    }
    public User(UserType userType) {
        this.userType = userType;
    }

    public User (String firstNameInput, String lastNameInput, String emailInput,
                 String usernameInput, UserType userTypeInput) {

        this.username = usernameInput;
        this.firstName = firstNameInput;
        this.lastName = lastNameInput;
        this.email = emailInput;
        this.userType = userTypeInput;
    }

    /***Setters*************************************************/


    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


    /***Getters*************************************************/


    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public UserType getUserType() { return this.userType; }
}
