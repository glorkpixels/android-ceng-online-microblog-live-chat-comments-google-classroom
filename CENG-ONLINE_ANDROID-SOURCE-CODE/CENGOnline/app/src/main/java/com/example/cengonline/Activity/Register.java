package com.example.cengonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cengonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    EditText Name, Surname, Email, Password, StudentOrTeacher;
    Button Register;
    TextView backToLogin;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference referenceStudent, referenceTeacher;


    //getting every element from xml on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name = findViewById(R.id.name);
        Surname = findViewById(R.id.surname);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        StudentOrTeacher = findViewById(R.id.studentOrTeacher);
        Register = findViewById(R.id.register);
        backToLogin = findViewById(R.id.backToLogin);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        referenceStudent = FirebaseDatabase.getInstance().getReference().child("Student");
        referenceTeacher = FirebaseDatabase.getInstance().getReference().child("Teacher");


        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
/*
            if there is no user id on that client*/
        }

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    // listen register button and make sure its all filled

                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                final String name = Name.getText().toString();
                final String surname = Surname.getText().toString();
                final String studentOrTeacher = StudentOrTeacher.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Email.setError("Please enter an email.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("Please enter a password..");
                    return;
                }
                if (password.length() < 8) {
                    Password.setError("Incorrect Form Of A Password.");
                    return;
                }

                if (TextUtils.isEmpty(studentOrTeacher))// tüm attributelar için buralar  eklenebeilir.
                {
                    Email.setError("Please fill in the blanks.");
                    return;
                }
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))//buralar eklencek
                {
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // if clicked create student or teacher on chosen  kind
                            switch (studentOrTeacher.toLowerCase()) {
                                case "student":
                                    String emailStudent = fAuth.getCurrentUser().getUid();
                                    DatabaseReference currentStudent = referenceStudent.child(emailStudent);

                                    currentStudent.child("UserKey").setValue(emailStudent);
                                    currentStudent.child("Name").setValue(name);
                                    currentStudent.child("Surname").setValue(surname);
                                    currentStudent.child("Email").setValue(email);
                                    currentStudent.child("Password").setValue(password);
                                    currentStudent.child("UserType").setValue(studentOrTeacher);
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                    Toast.makeText(Register.this, "Student Inserted To The Database.", Toast.LENGTH_LONG).show();
                                    updateUI();
                                    break;

                                case "teacher":


                                    String emailTeacher = fAuth.getCurrentUser().getUid();
                                    DatabaseReference currentTeacher = referenceTeacher.child(emailTeacher);

                                    currentTeacher.child("UserKey").setValue(emailTeacher);
                                    currentTeacher.child("Name").setValue(name);
                                    currentTeacher.child("Surname").setValue(surname);
                                    currentTeacher.child("Email").setValue(email);
                                    currentTeacher.child("Password").setValue(password);
                                    currentTeacher.child("UserType").setValue(studentOrTeacher);
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                    Toast.makeText(Register.this, "Teacher Inserted To The Database.", Toast.LENGTH_LONG).show();
                                    updateUI();
                                    break;


                                default: //Go back you did something wrong
                                    startActivity(new Intent(getApplicationContext(), Register.class));
                                    break;

                            }

                        }
                    });
                } else {
                    Toast.makeText(Register.this, "Complete all fields", Toast.LENGTH_SHORT).show();
                    //you need to fill everything ¯\_(ツ)_/¯
                }
            }

        });


        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Login.class));// Login Classına yönlendirilmesi gerek

            }
        });
    }

    private void updateUI() {

        Intent homeActivity = new Intent(getApplicationContext(), Login.class);
        startActivity(homeActivity);
        finish();
        //update ui because register success and login ¯\_(ツ)_/¯

    }
}
