package com.example.cengonline.Models;

public class Chat {


    // chat users model to pull from database to messaging classes
    private String UserKey;
    private String Email;
    private String Name;
    private String Surname;
    private String UserType;
    private String Password;

    public Chat(String email, String name, String surname, String userType, String password, String userkey) {
        Email = email;
        Name = name;
        Surname = surname;
        UserType = userType;
        Password = password;
        UserKey = userkey;
    }

    public Chat(){

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }
}
