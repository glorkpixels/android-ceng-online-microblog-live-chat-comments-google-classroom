package com.example.cengonline.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cengonline.Activity.ChatDetailActivity;
import com.example.cengonline.Activity.Person;
import com.example.cengonline.Activity.PostDetailActivity;
import com.example.cengonline.Activity.Student;
import com.example.cengonline.Fragments.MessageFragment;

import com.example.cengonline.Models.Chat;
import com.example.cengonline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ChatAdapter extends  RecyclerView.Adapter<ChatAdapter.MyViewHolder>{

// showing all people on chat list
    Context mContext;
    List<Chat> mData ;
    public String courseID;

    RecyclerView chatRecyclerView ;
    MessageAdapter messageAdapter ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    FirebaseAuth mAuth;
    FirebaseUser currentUser ;


    public ChatAdapter(Context mContext, List<Chat> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(mContext).inflate(R.layout.row_chat_item,parent,false);
        return new ChatAdapter.MyViewHolder(row);
    }



    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {

    //    holder.tvTitle.setText(mData.get(position).getReceiver());
      // Glide.with(mContext).load(mData.get(position).getPicture()).into(holder.imgPost);
    holder.tvTitle.setText((mData.get(position).getName() + " "+ mData.get(position).getSurname()).toUpperCase() + " - "  + mData.get(position).getUserType().toUpperCase());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.row_chat_person);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent chatDetailActivity = new Intent(mContext, ChatDetailActivity.class);
                    int position = getAdapterPosition();


                    chatDetailActivity.putExtra("sent",mData.get(position).getUserKey());


                    chatDetailActivity.putExtra("namesur",mData.get(position).getName() +" " + mData.get(position).getSurname());
                    mContext.startActivity(chatDetailActivity);



                }
            });

        }


    }
}
