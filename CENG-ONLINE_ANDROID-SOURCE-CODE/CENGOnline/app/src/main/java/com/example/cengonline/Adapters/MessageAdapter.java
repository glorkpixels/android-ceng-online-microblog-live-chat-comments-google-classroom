package com.example.cengonline.Adapters;


import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cengonline.Models.Comment;
import com.example.cengonline.Models.Message;
import com.example.cengonline.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private Context mContext;
    private List<Message> mData;
    private String name;
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;
// this adapter takes all messages between two individuals and puts it on view according to sent or received info

    public MessageAdapter(Context mContext, List<Message> mData, String name) {
        this.mContext = mContext;
        this.mData = mData;
        this.name = name;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View viewChatMine = null;
// deciding messages are sent or received and calling respective layouts to put on recycler view
        switch (viewType) {
            case VIEW_TYPE_ME:
                viewChatMine = layoutInflater.inflate(R.layout.row_message_item_sent, parent, false);

                break;
            case VIEW_TYPE_OTHER:
                viewChatMine = layoutInflater.inflate(R.layout.row_message_item_receive, parent, false);

                break;
        }


        // View row = LayoutInflater.from(mContext).inflate(R.layout.row_message_item_receive,parent,false);

        return new MessageViewHolder(viewChatMine);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {


        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(mData.get(position).getSender())) {

            holder.tv_name.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        } else {
            holder.tv_name.setText(name);
        }
        holder.tv_content.setText(mData.get(position).getMessage());
        holder.tv_date.setText(timestampToString((Long) mData.get(position).getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public int getItemViewType(int position) {

        // deciding sender or receiver
        if (TextUtils.equals(mData.get(position).getSender(), FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        ImageView img_user;
        TextView tv_name, tv_content, tv_date;

        public MessageViewHolder(View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.message_user_image);
            tv_name = itemView.findViewById(R.id.message_user_name);
            tv_content = itemView.findViewById(R.id.message_content);
            tv_date = itemView.findViewById(R.id.message_date);
        }
    }


    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("hh:mm", calendar).toString();
        return date;


    }


}

