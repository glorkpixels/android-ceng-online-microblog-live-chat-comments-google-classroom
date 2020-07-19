package com.example.cengonline.Activity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cengonline.Adapters.CommentAdapter;
import com.example.cengonline.Adapters.MessageAdapter;
import com.example.cengonline.Models.Comment;
import com.example.cengonline.Models.Message;
import com.example.cengonline.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChatDetailActivity extends AppCompatActivity {


    /*
    * This class is for chat details after user selected whom to send message
    * it interperets layout chat details activity_chat_detail.xml has a recycler view inside
    * that let's us add every message individually to that view which can be infinite basically.
    *
    *
    * In  iniRvComment we sent those messages came to from selected user to current or sent to selected user
    *
    *
    * below there is necessary
    *
    * */
    private static final String TAG = "MainActivity";
    static String MESSAGE_KEY = "Message";
    EditText editTextComment;
    Button btnSentMessage;
    String peopleKey;
    String senderKey;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView rvchat;
    MessageAdapter messageAdapter;
    List<Message> listMessage;


    String NAME = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);


        // let's set the statue bar to transparent


        // ini Views
        rvchat = findViewById(R.id.rv_message);


        editTextComment = findViewById(R.id.chat_message_detail);
        btnSentMessage = findViewById(R.id.chat_send_message);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        senderKey = firebaseUser.getUid();

        //button and edit text declearations
        // add Comment button click listner

        peopleKey = getIntent().getExtras().getString("sent");
        String postDescription = getIntent().getExtras().getString("receive");
        String postTitle = getIntent().getExtras().getString("message");

        // sent button listener that can crreate new message object if it is clicked and pushes it to datareference decleared.
        btnSentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSentMessage.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = firebaseDatabase.getReference(MESSAGE_KEY).push();


                String comment_content = editTextComment.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getEmail();
                String uimg = "";
                Message comment = new Message(peopleKey, senderKey, comment_content);


                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("message sent ");
                        editTextComment.setText("");
                        btnSentMessage.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("fail to add comment : " + e.getMessage());
                    }
                });


            }
        });


        String postUNAME = getIntent().getExtras().getString("userId");

        iniRvComment();


    }


    private void iniRvComment() {

        rvchat.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference(MESSAGE_KEY);

        DatabaseReference commentRef2 = firebaseDatabase.getReference(MESSAGE_KEY);

// below is the message handler that can take messages between two individuals and add them to a list to be adapted to
        // corresponding xml schemas which are row_message_item_(receive or send).xml
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMessage = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Message message = snap.getValue(Message.class);

                    String userNow = firebaseUser.getUid();
                    String userClicked = peopleKey;

            // getting every user but current user to be llisted
                    if (!userNow.equals(userClicked)) {
                        if (userNow.equals(message.getSender())) {
                            if (userClicked.equals(message.getReceiver())) {
                                listMessage.add(message);
                                System.out.println("NEDEN");


                            }
                        } else {
                            if (userNow.equals(message.getReceiver())) {
                                if (userClicked.equals(message.getSender())) {
                                    listMessage.add(message);

                                    System.out.println("OLMUYOR");
                                }
                            }
                        }

                    }


                }

                String name = getIntent().getExtras().getString("namesur");

                getSupportActionBar().setTitle(name);

                //this part we get to message adapter
                messageAdapter = new MessageAdapter(getApplicationContext(), listMessage, name);
                rvchat.setAdapter(messageAdapter);
                // if clicked on a user we get their messages with each other and call  message adapter that will adapt sender
                // or receiver bubbles on recycler layout

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void showMessage(String message) {
    // floating feed back messages from  system
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }


    private String timestampToString(long time) {
    // time stamp converter
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        return date;


    }


}
