package com.practice.compass.network.request.SeeIssueRequest;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;

public class SeeIssueResponse implements Serializable{

    @Expose
    String id;

    @Expose
    String postDesc;
    public void setId(String id) {
        this.id = id;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getId(){
        return id;
    }

    public String getPostDesc(){
        return postDesc;
    }

    @Expose
    public User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Expose
    public ArrayList<Images> images;

    public void setImages(ArrayList<Images> images) {
        this.images = images;
    }

    public ArrayList<Images> getImages() {
        return images;
    }

    @Expose
    public ArrayList<String> loc;

    public void setLoc(ArrayList<String> loc) {
        this.loc = loc;
    }

    public ArrayList<String> getLoc() {
        return loc;
    }


    @SuppressWarnings("serial")
    public static class AsList extends ArrayList<SeeIssueResponse> { }
}
