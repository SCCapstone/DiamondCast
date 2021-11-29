package main.java;

/**************************************************************
    *User Class for CSCE490 Project 
    *@author by Daniel McKenna
    *@version 1.0.0 11-28-21
    *Copyright tbd
***************************************************************/
/* Class for creating a standard user in the app to send to database 
    Methods
        -User(): constructor
        -getProfileInformation
        -getProfileInformation: 
        -isStandardUser: returns a bool based on status
        -isStaff: returns a bool based on status
        -isAdmin: returns a bool based on status
    Instance Variables */

public class User {
    //instance variables 
    private string id;
    private string username;
    private string fullName;
    private string email;
    private string phoneNumber;
    private string password;       //this is on UML but I don't want to store this without hashing 
    private Enum userType;       //add get and set
    private bool twoFactorAuth;

    // Constructor
    public User() {
        this.id = null; 
    }
    
    /***Setters*************************************************/
    protected void setId(string id) {
        this.id = id;
    }

    protected void setUsername(string username) {
        this.username = username;
    }

    protected void setFullName(string fullName) {
        this.fullName = fullName;
    }

    protected void setEmail(string email) {
        this.email = email;
    }

    protected void setPhoneNumber(string phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    protected void setTwoFactorAuth(bool twoFactorAuth) {
        this.twoFactorAuth = twoFactorAuth;
    }
    
    /***Getters*************************************************/
    protected string getId() {
        return this.id;
    }





}


