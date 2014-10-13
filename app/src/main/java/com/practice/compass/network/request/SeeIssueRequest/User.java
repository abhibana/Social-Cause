package com.practice.compass.network.request.SeeIssueRequest;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
    @Expose
    String id;
    @Expose
    String username;
    @Expose
    String email;
    @Expose
    String mobileNumber;
    @Expose
    ArrayList<String> posts;

    public void setId(String id){
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPosts(ArrayList<String> posts) {
        this.posts = posts;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getPosts() {
        return posts;
    }

    public String getUsername() {
        return username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}
