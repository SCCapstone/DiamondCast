package com.example.diamondcastapp;

/**************************************************************
    *User Class for CSCE490 Project 
    *@author Daniel McKenna & Frank 
    *@version 1.0.0 11-28-21
    *Copyright tbd
***************************************************************/
/* Class for a user in the app 
    this class contains a a defualt and a normal constructor
     and getters and setters for all instance veriables */

    public class User {

        //instance variables 
        private string uid;
        private string username;
        private string firstName;
        private string lastName;
        private string email;
        private Enum userType;       
    
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
    protected void setId(string uid) {
        this.uid = uid;
    }

    protected void setUsername(string username) {
        this.username = username;
    }

    protected void setFullName(string firstName) {
        this.firstName = firstName;
    }

    protected void setFullName(string lastName) {
        this.lastName = lastName;
    }

    protected void setEmail(string email) {
        this.email = email;
    }

    protected void setUserType(UserType userType) {
        this.userType = userType;
    }

    
    /***Getters*************************************************/
    protected string getUid() {
        return this.uid;
    }

    protected string getUsername() {
        return this.getUsername();
    }

    protected string getFirstName() {
        return this.firstName;
    }

    protected string getLastName(){
        return this.lastName;
    }

    protected string getEmail() {
        return this.email;
    }

    protected UserType getUserType() {
        return this.userType;
    }

}
