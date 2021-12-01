package com.example.diamondcastapp;

/**************************************************************
    *Class for registering a user with firebase database 
    *@author Daniel McKenna 
    *@version 1.0.0 11-28-21
    *Copyright tbd
***************************************************************/

public class registerUser {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private UserType userType;

    public registerUser () {
        this.username = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.userType = UserType.Client;
    }

    public registerUser (String firstNameInput, String lastNameInput, String emailInput,
                 String usernameInput, String passwordInput, UserType userTypeInput) {
        this.firstName = firstNameInput;
        this.lastName = lastNameInput;
        this.email = emailInput;
        this.username = usernameInput;
        this.password = passwordInput;
        this.userType = userTypeInput;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String passwordForCreatingAccount() {
        return password;
    }
}
