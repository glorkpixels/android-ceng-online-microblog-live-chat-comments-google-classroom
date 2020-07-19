package com.example.cengonline.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cengonline.R;

public class Teacher extends Person {

    // teacher that extends person for register
    TextView Teacher;
    Button logoff;

    public Teacher(String name, String surname, String email, String password, String userType) {
        super(name, surname, email, password, userType);
    }

    public Teacher() {

    }

    @Override
    public String getUserKey() {
        return super.getUserKey();
    }

    @Override
    public void setUserKey(String userKey) {
        super.setUserKey(userKey);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getSurname() {
        return super.getSurname();
    }

    @Override
    public void setSurname(String surname) {
        super.setSurname(surname);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getUserType() {
        return super.getUserType();
    }

    @Override
    public void setUserType(String userType) {
        super.setUserType(userType);
    }

    @Override
    public void logout(View view) {
        super.logout(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Toast.makeText(Teacher.this, "THIS IS TEACHER CLASS.", Toast.LENGTH_LONG).show();
        logoff = findViewById(R.id.logOffTeacher);
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });

    }


}
