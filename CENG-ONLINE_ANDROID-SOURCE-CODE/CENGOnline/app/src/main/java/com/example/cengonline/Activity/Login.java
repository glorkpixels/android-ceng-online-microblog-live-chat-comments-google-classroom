package com.example.cengonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;



public class Login extends AppCompatActivity {
/*
*
* Login is the first activity user sees if user is not logged in
* if it is already logged in it passes this one
*
*
* */
    EditText EmailLogin, PasswordLogin;
    Button LoginAsStudent;
    //Button LoginAsTeacher;
    TextView CreateAccount;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference referenceStudent, referenceTeacher;
    FirebaseUser student,teacher;
    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailLogin = findViewById(R.id.emailLogin);
        PasswordLogin = findViewById(R.id.passwordLogin);
        fAuth = FirebaseAuth.getInstance();
        LoginAsStudent = findViewById(R.id.loginAsStudent);
        CreateAccount = findViewById(R.id.createAccount);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        referenceStudent = FirebaseDatabase.getInstance().getReference().child("Student");
        referenceTeacher = FirebaseDatabase.getInstance().getReference().child("Teacher");

        //We get both student and teacher references to decide if logger is student or teacher ¯\_(ツ)_/¯
        if( fAuth.getCurrentUser()!= null)
        {
            Intent i = new Intent(Login.this, Register.class);
            startActivity(i);
            finish();
        }

        LoginAsStudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                email = EmailLogin.getText().toString().trim();
                password = PasswordLogin.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    EmailLogin.setError("Please enter an email.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    PasswordLogin.setError("Please enter a password..");
                    return;
                }
                if (password.length() < 8) ////PASSWORD EŞİTSE OLACAK
                {
                    PasswordLogin.setError("Incorrect Form Of A Password.");
                    return;
                }
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                {

                    loginFunction();

                } else
                {

                    Toast.makeText(Login.this, "Complete all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        CreateAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), Register.class));// Register Classına yönlendirilmesi gerek
            }
        });

    }

    private void loginFunction() {

        // login function
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login.this,
                new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {

                            Intent i = new Intent(Login.this, MainActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }



}
