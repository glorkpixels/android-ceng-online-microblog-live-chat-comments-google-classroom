package com.example.cengonline.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cengonline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    Button LogOff;
    FirebaseAuth fAuth;
    FirebaseFirestore  fStore;
    DatabaseReference referenceStudent, referenceTeacher;
    FirebaseUser student,teacher;

     DatabaseReference mDatabaseUsers;
    TextView TextForCheckingbla;
    TextView Textytext;
    String name;
    String deneme;
    String te;
    String st;
    private static final String TAG = "MainActivity";


    /*
    *
    * This activity is a loading screen basically
    * we sent welcome message
    * we learn logger type student or teacher and
    * sent them to their drawer classes based on their user type
    *
    *
    *
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // welcome screen
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
         final String UID = user.getUid();

        fAuth = FirebaseAuth.getInstance();

// looking if user is teacher
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Teacher/").child(fAuth.getCurrentUser().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    name = dataSnapshot.child("UserType").getValue(String.class);

                    te=name;
                    if(te != null)
                    {
                        // if it is passing to teacher drawer
                        Intent teacherIntent = new Intent(getApplicationContext(), TeacherDrawer.class);
                        startActivity(teacherIntent);
                        finish();
                    }
                    System.out.println(te);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }});


            // looking if its student
                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Student/").child(fAuth.getCurrentUser().getUid());
                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        name = dataSnapshot2.child("UserType").getValue(String.class);

                        st=name;
                        if(st != null)
                        {
                            // if student calling student drawer
                            Intent studentIntent = new Intent(getApplicationContext(), StudentDrawer.class);
                            startActivity(studentIntent);
                            finish();
                        }
                        System.out.println(st);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }});






    }


}
