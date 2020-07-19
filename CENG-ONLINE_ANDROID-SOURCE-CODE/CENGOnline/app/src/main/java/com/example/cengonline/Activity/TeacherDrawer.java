package com.example.cengonline.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
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
import com.example.cengonline.Fragments.HomeFragment_Teacher;
import com.example.cengonline.Fragments.MessageFragment;
import com.example.cengonline.Fragments.PostFragment_Teacher;
import com.example.cengonline.Fragments.ProfileFragment;
import com.example.cengonline.Models.Course;
import com.example.cengonline.Models.Post;
import com.example.cengonline.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TeacherDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    //teacher main activity to add courses
    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth mAuth;
    FirebaseUser currentUser ;
    String name;

    String courseID;

    Dialog popAddCourse ;
    ImageView popupPostImage,popupAddBtn;
    TextView popupTitle,popupDescription;
    ProgressBar popupClickProgress;

    Dialog popAddPost ;
    ImageView popupPostIm,popupAdd;
    TextView popupTit,popupDesc;
    ProgressBar popupClickProg;

    TextView lol;
    String ce;

    private Uri pickedImgUri = null;

    private static final int PReqCode = 22;
    private static final int REQUESTCODE = 22 ;


    private static final String TAG = "TeacherDrawer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        final String UID = currentUser.getUid();
        setContentView(R.layout.activity_teacher_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ini popup
        iniPopup();
        setupPopupImageClick();


// fab button functions according to the fragment they are in if they are in we call popaddcourse
        // if thet are inside of a course we call popaddpost
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
                if (fragment != null && fragment.isVisible()) {
                    if (fragment instanceof HomeFragment_Teacher) {

                        popAddCourse.show();
                    } else if(fragment instanceof PostFragment_Teacher) {

                        lol=findViewById(R.id.textView3);
                       ce= lol.getText().toString();
                        iniPopupPost();
                        setupPopupImageClickp2();
                       popAddPost.show();
                        System.out.println(ce);
                    }
                }




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
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment_Teacher()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher_drawer, menu);
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



    private void setupPopupImageClick() {


        popupPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here when image clicked we need to open the gallery
                // before we open the gallery we need to check if our app have the access to user files
                // we did this before in register activity I'm just going to copy the code to save time ...
           if (Build.VERSION.SDK_INT >= 22){
       checkAndRequestForPermission();
}
            else{
                openGallery();
            }


            }

        });




    }

    private void checkAndRequestForPermission() {
// file operation permission on phone

        if (ContextCompat.checkSelfPermission(TeacherDrawer.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(TeacherDrawer.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(TeacherDrawer.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(TeacherDrawer.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            // everything goes well : we have permission to access user gallery
            openGallery();

    }

    // when user picked an image ...
    //if image picked uploading it
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null ) {

            pickedImgUri = data.getData() ;
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if (fragment != null && fragment.isVisible()) {
                if (fragment instanceof HomeFragment_Teacher) {

                    popupPostImage.setImageURI(pickedImgUri);
                } else if(fragment instanceof PostFragment_Teacher) {


                    popupPostIm.setImageURI(pickedImgUri);
                }
            }


        }


    }



    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !
// gallery function that lets us select image and gives us image path
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PReqCode);
    }


    private void iniPopup() {

        // popup layout caller for course creation
        popAddCourse = new Dialog(this);
        popAddCourse.setContentView(R.layout.popup_add_course);
        popAddCourse.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddCourse.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddCourse.getWindow().getAttributes().gravity = Gravity.TOP;

        // ini popup widgets
        popupPostImage = popAddCourse.findViewById(R.id.popup_img);
        popupTitle = popAddCourse.findViewById(R.id.popup_title);
        popupDescription = popAddCourse.findViewById(R.id.popup_description);
        popupAddBtn = popAddCourse.findViewById(R.id.popup_add);
        popupClickProgress = popAddCourse.findViewById(R.id.popup_progressBar);


        // Add post click Listener

        popupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupAddBtn.setVisibility(View.INVISIBLE);
                popupClickProgress.setVisibility(View.VISIBLE);

                // we need to test all input fields (Title and description ) and post image

                if (!popupTitle.getText().toString().isEmpty()
                        && !popupDescription.getText().toString().isEmpty()
                        && pickedImgUri != null ) {

                    //everything is okey no empty or null value
                    // TODO Create Post Object and add it to firebase database
                    // first we need to upload post Image
                    // access firebase storage
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("course_images");
                    final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageDownloadLink = uri.toString();
                                    // create post Object
                                    Course coursex = new Course(popupTitle.getText().toString(),
                                            popupDescription.getText().toString(),
                                            imageDownloadLink);

                                    // Add post to firebase database

                                    addCourse(coursex);



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // something goes wrong uploading picture

                                    showMessage(e.getMessage());
                                    popupClickProgress.setVisibility(View.INVISIBLE);
                                    popupAddBtn.setVisibility(View.VISIBLE);



                                }
                            });


                        }
                    });








                }
                else {
                    showMessage("Please verify all input fields and choose Course Image") ;
                    popupAddBtn.setVisibility(View.VISIBLE);
                    popupClickProgress.setVisibility(View.INVISIBLE);

                }



            }
        });



    }

    private void addCourse(Course course) {

        // popup layout caller for post creation inside course
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Course").push();

        // get post unique ID and upadte post key
        String key = myRef.getKey();
        course.setCourseKey(key);


        // add post data to firebase database

        myRef.setValue(course).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Course Added successfully");
                popupClickProgress.setVisibility(View.INVISIBLE);
                popupAddBtn.setVisibility(View.VISIBLE);
                popAddCourse.dismiss();
            }
        });





    }


    private void showMessage(String message) {

        Toast.makeText(TeacherDrawer.this,message,Toast.LENGTH_LONG).show();

    }


    private void iniPopupPost() {
 // inside classes we change layout of popup to addd post
        popAddPost = new Dialog(this);
        popAddPost.setContentView(R.layout.popup_add_post);
        popAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddPost.getWindow().getAttributes().gravity = Gravity.TOP;

        // ini popup widgets
        popupPostIm = popAddPost.findViewById(R.id.popup_img);
        popupTit = popAddPost.findViewById(R.id.popup_title);
        popupDesc = popAddPost.findViewById(R.id.popup_description);
        popupAdd = popAddPost.findViewById(R.id.popup_add);
        popupClickProg = popAddPost.findViewById(R.id.popup_progressBar);


        // Add post click Listener

        popupAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupAdd.setVisibility(View.INVISIBLE);
                popupClickProg.setVisibility(View.VISIBLE);

                // we need to test all input fields (Title and description ) and post image

                if (!popupTit.getText().toString().isEmpty()
                        && !popupDesc.getText().toString().isEmpty()
                        && pickedImgUri != null ) {

                    //everything is okey no empty or null value
                    // TODO Create Post Object and add it to firebase database
                    // first we need to upload post Image
                    // access firebase storage
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("post_images");
                    final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageDownloadLink = uri.toString();
                                    // create post Object
                                    Post post = new Post(popupTit.getText().toString(),
                                            popupDesc.getText().toString(),
                                            imageDownloadLink,currentUser.getUid());


                                    // Add post to firebase database

                                    addPost(post);



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // something goes wrong uploading picture

                                    showMessage(e.getMessage());
                                    popupClickProg.setVisibility(View.INVISIBLE);
                                    popupAdd.setVisibility(View.VISIBLE);



                                }
                            });


                        }
                    });








                }
                else {
                    showMessage("Please verify all input fields and choose Post Image") ;
                    popupAdd.setVisibility(View.VISIBLE);
                    popupClickProg.setVisibility(View.INVISIBLE);

                }



            }
        });



    }


    private void addPost(Post post) {
 // adding post to database under course with post id
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Course").child(ce).child("Posts").push();

        // get post unique ID and upadte post key
        String key = myRef.getKey();
        post.setPostKey(key);


        // add post data to firebase database

        myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Post Added successfully");
                popupClickProg.setVisibility(View.INVISIBLE);
                popupAdd.setVisibility(View.VISIBLE);
                popAddPost.dismiss();
            }
        });





    }
    private void setupPopupImageClickp2() {
//  image area click listener

        popupPostIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here when image clicked we need to open the gallery
                // before we open the gallery we need to check if our app have the access to user files
                // we did this before in register activity I'm just going to copy the code to save time ...
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                } else {
                    openGallery();
                }


            }

        });

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here
        // on drawer
        int id = item.getItemId();

        if (id == R.id.HomeTeacherFragment) {
            getSupportActionBar().setTitle("Courses");
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment_Teacher()).commit();

        } else if (id == R.id.ProfileTeacherFragment) {

            getSupportActionBar().setTitle("Teacher Profile ");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();

        }
        else if (id == R.id.MessageTeacherFragment) {

            getSupportActionBar().setTitle("CHAT");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new MessageFragment())
                    .commit();


        }
        else if (id == R.id.SignoutTeacher) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(TeacherDrawer.this, Login.class);
            startActivity(i);
            finish();


            Intent loginActivity = new Intent(getApplicationContext(),Login.class);
            startActivity(loginActivity);
            finish();





        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader() {

        // drawer header update with user infos
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView teacher_name = headerView.findViewById(R.id.teacher_name);
        TextView teacher_mail = headerView.findViewById(R.id.teacher_mail);
        ImageView student_photo = headerView.findViewById(R.id.teacher_photo);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Teacher").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("Name").getValue(String.class);
                name +=" "+dataSnapshot.child("Surname").getValue(String.class);
                System.out.println("OF"+name);
                teacher_name.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }});



        teacher_mail.setText(currentUser.getEmail());




    }
}
