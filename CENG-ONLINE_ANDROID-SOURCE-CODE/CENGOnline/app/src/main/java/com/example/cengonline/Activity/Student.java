package com.example.cengonline.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cengonline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Student extends Person {

    EditText EmailLogin, PasswordLogin;
    Button LoginAsStudent;
    //Button LoginAsTeacher;
    TextView CreateAccount;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference referenceStudent, referenceTeacher;
    FirebaseUser student, teacher;
    private String email, password;
    Button logoff;
    EditText sa;

    // student that extends person for register
    /*
    public Student(String name, String surname, String email, String password, String userType) {
        super(name, surname, email, password, userType);
    }*/

    public Student() {
        super();
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
        setContentView(R.layout.activity_student);
        Toast.makeText(Student.this, "THIS IS STUDENT CLASS.", Toast.LENGTH_LONG).show();// bu sonrdan silinecek


        logoff = findViewById(R.id.logOffStudent);
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });
    }
}
