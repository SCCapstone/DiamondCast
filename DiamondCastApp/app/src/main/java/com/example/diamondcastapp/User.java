package com.example.diamondcastapp;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private UserType userType;

    public User () {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.username = "";
        this.password = "";
        this.userType = UserType.Client;
    }

    public User (String firstNameInput, String lastNameInput, String emailInput,
                 String usernameInput, String passwordInput, UserType userTypeInput) {
        this.firstName = firstNameInput;
        this.lastName = lastNameInput;
        this.email = emailInput;
        this.username = usernameInput;
        this.password = passwordInput;
        this.userType = userTypeInput;
    }

}
