package com.practice.compass.network.request.SeeIssueRequest;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Images implements Serializable{

    @Expose
    String id;
    @Expose
    String imageUrl;
    @Expose
    String thumbnailUrl;

    public void setId(String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}

