package com.example.cengonline.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cengonline.Fragments.HomeFragment;
import com.example.cengonline.Fragments.MessageFragment;
import com.example.cengonline.Fragments.ProfileFragment;
import com.example.cengonline.Models.cList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.cengonline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StudentDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String name;

    String courseID;


    Dialog popAddCourse;
    TextView popupTitle;
    ImageView popupAddBtn;
    ProgressBar popupClickProgress;
    TextView lol;
    String ce;

    private Uri pickedImgUri = null;


    private static final String TAG = "StudentDrawer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        final String UID = currentUser.getUid();
        setContentView(R.layout.activity_student_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        iniPopup();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popAddCourse.show();


            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        updateNavHeader();


        getSupportActionBar().setTitle("Courses");
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // drawer click listener that sends to clicked fragment

        if (id == R.id.HomeStudentFragment) {
            getSupportActionBar().setTitle("Courses");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        } else if (id == R.id.ProfileStudentFragment) {

            getSupportActionBar().setTitle("Student Profile ");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();

        } else if (id == R.id.MessageStudentFragment) {

            getSupportActionBar().setTitle("CHAT");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new MessageFragment())
                    .commit();


        } else if (id == R.id.SignoutStudent) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(StudentDrawer.this, Login.class);
            startActivity(i);
            finish();


            Intent loginActivity = new Intent(getApplicationContext(), Login.class);
            startActivity(loginActivity);
            finish();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView student_name = headerView.findViewById(R.id.student_name);
        TextView student_mail = headerView.findViewById(R.id.student_mail);
        ImageView student_photo = headerView.findViewById(R.id.student_photo);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("Name").getValue(String.class);
                name += " " + dataSnapshot.child("Surname").getValue(String.class);
                System.out.println("OF" + name);
                student_name.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        student_mail.setText(currentUser.getEmail());

 // drawer user credentials setting from database pulling current users info
    }


    private void iniPopup() {

        popAddCourse = new Dialog(this);
        popAddCourse.setContentView(R.layout.popup_join_course);
        popAddCourse.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddCourse.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popAddCourse.getWindow().getAttributes().gravity = Gravity.TOP;

        // ini popup widgets
        popupTitle = popAddCourse.findViewById(R.id.enter_course);
        popupAddBtn = popAddCourse.findViewById(R.id.popup_addxd);
        popupClickProgress = popAddCourse.findViewById(R.id.popup_progressBarxd);

        // Add post click Listener

        popupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // joining class button listener when its complete
                popupAddBtn.setVisibility(View.INVISIBLE);
                popupClickProgress.setVisibility(View.VISIBLE);

                // we need to test all input fields (Title and description ) and post image

                if (!popupTitle.getText().toString().isEmpty()) {


                    cList user = new cList(popupTitle.getText().toString());
                    popupAddBtn.setVisibility(View.VISIBLE);
                    joinClass(user);
// it calls join class

                } else {
                    showMessage("Please verify course code");
                    popupAddBtn.setVisibility(View.VISIBLE);
                    popupClickProgress.setVisibility(View.INVISIBLE);

                }


            }
        });


    }

    private void joinClass(cList ckey) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("StudentCourseList").child(currentUser.getUid()).push();

        // get post unique ID and upadte post key
        // add course data of student to his couse list on firebase database

        myRef.setValue(ckey).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Joined Course Successfully");
                popupClickProgress.setVisibility(View.INVISIBLE);
                popupAddBtn.setVisibility(View.VISIBLE);
                popAddCourse.dismiss();
            }
        });


    }

    private void showMessage(String message) {

        Toast.makeText(StudentDrawer.this, message, Toast.LENGTH_LONG).show();
        // floating feedback with toast
    }

}