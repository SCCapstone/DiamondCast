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
        private String uid;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private UserType userType;
    
    public User () {
        this.uid = "";
        this.username = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.userType = UserType.Client;
    }

    public User (String uid, String firstNameInput, String lastNameInput, String emailInput,
                 String usernameInput, UserType userTypeInput) {
        this.uid = uid;
        this.username = usernameInput;
        this.firstName = firstNameInput;
        this.lastName = lastNameInput;
        this.email = emailInput;
        this.userType = userTypeInput;
    }

    /***Setters*************************************************/
    protected void setId(String uid) {
        this.uid = uid;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    protected void setUserType(UserType userType) {
        this.userType = userType;
    }

    
    /***Getters*************************************************/
    protected String getUid() {
        return this.uid;
    }

    protected String getUsername() {
        return this.getUsername();
    }

    protected String getFirstName() {
        return this.firstName;
    }

    protected String getLastName(){
        return this.lastName;
    }

    protected String getEmail() {
        return this.email;
    }

    protected UserType getUserType() { return this.userType; }
}
