package com.example.cengonline.Models;

public class cList {
    String cKey;
// this is a model for our pull from database to decide whether a student take that class or not
    public cList(String cKey) {
        this.cKey = cKey;
    }
    public cList() {

    }

    public String getcKey() {
        return cKey;
    }

    public void setcKey(String cKey) {
        this.cKey = cKey;
    }
}
