package com.closetshare.app;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ApiViewCloset {

    @Expose
    private List<ClosetItem> result = new ArrayList<ClosetItem>();

    public List<ClosetItem> getResult() {
        return result;
    }

    public void setResult(List<ClosetItem> result) {
        this.result = result;
    }

}

class ClosetItem {

    @Expose
    private String photoId;

    @Expose
    private String descript;

    @Expose
    private String userId;

    @Expose
    private String username;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}