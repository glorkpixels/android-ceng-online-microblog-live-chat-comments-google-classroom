package com.example.cengonline.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cengonline.Models.Comment;
import com.example.cengonline.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

        private Context mContext;
        private List<Comment> mData;
    // comment adapter to show it off on post detail activity according to row_comment.xml

        public CommentAdapter(Context mContext, List<Comment> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @NonNull
        @Override
        public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(mContext).inflate(R.layout.row_comment,parent,false);
            return new CommentViewHolder(row);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

            holder.tv_name.setText(mData.get(position).getUname());
            holder.tv_content.setText(mData.get(position).getContent());
            holder.tv_date.setText(timestampToString((Long)mData.get(position).getTimestamp()));


        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class CommentViewHolder extends RecyclerView.ViewHolder{

            ImageView img_user;
            TextView tv_name,tv_content,tv_date;

            public CommentViewHolder(View itemView) {
                super(itemView);
                img_user = itemView.findViewById(R.id.comment_user_img);
                tv_name = itemView.findViewById(R.id.comment_username);
                tv_content = itemView.findViewById(R.id.comment_content);
                tv_date = itemView.findViewById(R.id.comment_date);
            }
        }



        private String timestampToString(long time) {

            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            calendar.setTimeInMillis(time);
            String date = DateFormat.format("hh:mm",calendar).toString();
            return date;


        }


    }

