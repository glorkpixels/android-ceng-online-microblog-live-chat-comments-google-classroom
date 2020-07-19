package com.example.cengonline.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cengonline.Activity.Login;
import com.example.cengonline.R;
import com.google.firebase.auth.FirebaseAuth;

public abstract class Person extends AppCompatActivity {
    public String UserKey;
    public String Name;
    public String Surname;
    public String Email;
    public String Password;
    public String UserType;
    Button logoffperson;
// person abstract to create teacher andstudent
    public Person(String name, String surname, String email, String password, String userType) {
        Name = name;
        Surname = surname;
        Email = email;
        Password = password;
        UserType = userType;
    }

    public Person() {

    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);


        logoffperson = findViewById(R.id.logOffPerson);
        logoffperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}
