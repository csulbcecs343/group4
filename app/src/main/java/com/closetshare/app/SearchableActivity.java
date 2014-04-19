package com.closetshare.app;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchableActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    /**
     * Method where the actual search is done.
     *
     * @param query is the search string.
     */
    private void doMySearch(String query) {
        Api api = Api.getInstance();

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("command", "search");
        options.put("query", query);

        ApiSearchResult result = api.search(options);

        Log.d("SEARCH", "Search results = " + String.valueOf(result.getResult().size()));
        ArrayList<String> sList = new ArrayList<String>();

        for (SearchResult curVal : result.getResult()) {
            if (curVal.getUsername().contains(query)) {
                sList.add(curVal.getUsername());
            }
        }
        
        Log.d("SEARCH", "Search: " + sList.toString());

    }
}
