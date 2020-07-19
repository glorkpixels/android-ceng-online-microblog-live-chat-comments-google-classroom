package com.example.cengonline.Models;

import com.google.firebase.database.ServerValue;
public class Course {

    // course creation and pull from database schema

    private String courseKey;
    private String title;
    private String description;
    private String picture;


    public Course() {
    }

    public Course( String title, String description, String picture) {
        this.title = title;
        this.description = description;
        this.picture = picture;
    }

    public String getCourseKey() {
        return courseKey;
    }

    public void setCourseKey(String postKey) {
        this.courseKey = postKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
