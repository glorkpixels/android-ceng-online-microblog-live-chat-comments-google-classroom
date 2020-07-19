package com.example.cengonline.Fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cengonline.Activity.Person;
import com.example.cengonline.Activity.Student;
import com.example.cengonline.Adapters.ChatAdapter;
import com.example.cengonline.Adapters.CommentAdapter;
import com.example.cengonline.Adapters.CourseAdapter;
import com.example.cengonline.Adapters.MessageAdapter;
import com.example.cengonline.Adapters.PostAdapter;
import com.example.cengonline.Models.Chat;
import com.example.cengonline.Models.Comment;
import com.example.cengonline.Models.Course;
import com.example.cengonline.Models.Message;
import com.example.cengonline.Models.Post;
import com.example.cengonline.R;
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

public class MessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MessageFragment.OnFragmentInteractionListener mListener;

    RecyclerView chatRecyclerView;
    ChatAdapter chatAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    DatabaseReference databaseReference2;
    List<Chat> chatList;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String title = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            title = bundle.get("courseID").toString();
        }


        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_message, container, false);
        chatRecyclerView = fragmentView.findViewById(R.id.chatRV);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatRecyclerView.setHasFixedSize(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Student");
        databaseReference2 = firebaseDatabase.getReference("Teacher");
        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        // Get List Posts from the database

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chatList = new ArrayList<>();
                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {

                    System.out.println(
                            postsnap.getClass().toString());
                    Chat chat = postsnap.getValue(Chat.class);
                    if (!chat.getUserKey().equals(currentUser.getUid())) {

                        chatList.add(chat);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {


                    Chat chat = postsnap.getValue(Chat.class);
                    if (!chat.getUserKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                        chatList.add(chat);
                        System.out.println(currentUser.getUid() + " OFFFF" + chat.getUserKey());

                    }


                }

                chatAdapter = new ChatAdapter(getActivity(), chatList);
                chatRecyclerView.setAdapter(chatAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}