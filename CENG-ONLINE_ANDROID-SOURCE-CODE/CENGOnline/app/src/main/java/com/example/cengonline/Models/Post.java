package com.example.cengonline.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ServerValue;

public class Post {

// po

        private String postKey;
        private String title;
        private String description;
        private String picture;
        private String userId;
        private String uName;
        private Object timeStamp ;
        FirebaseAuth mAuth;
        FirebaseUser currentUser ;
 // post creation and pull from database schema

        public Post(String title, String description, String picture, String userId ) {

            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();
            this.title = title;
            this.description = description;
            this.picture = picture;
            this.userId = userId;
            this.timeStamp = ServerValue.TIMESTAMP;
            this.uName = currentUser.getEmail();
        }

        // make sure to have an empty constructor inside ur model class
        public Post() {
        }

    public String getuName() {
        return uName;
    }

    public String getPostKey() {
            return postKey;
        }

        public void setPostKey(String postKey) {
            this.postKey = postKey;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getPicture() {
            return picture;
        }

        public String getUserId() {
            return userId;
        }


        public Object getTimeStamp() {
            return timeStamp;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setTimeStamp(Object timeStamp) {
            this.timeStamp = timeStamp;
        }
    }

