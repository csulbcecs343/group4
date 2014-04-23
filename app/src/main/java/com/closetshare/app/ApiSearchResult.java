package com.closetshare.app;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ApiSearchResult {

    @Expose
    private List<SearchResult> result = new ArrayList<SearchResult>();

    public List<SearchResult> getResult() {
        return result;
    }

    public void setResult(List<SearchResult> result) {
        this.result = result;
    }

}

class SearchResult {

    @Expose
    private String username;

    @Expose
    private String userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}