package com.closetshare.app;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchableActivity extends ListActivity {

    private SearchUserTask mSearchTask = null;

    private ArrayList<String> mList = new ArrayList<String>();

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);
        setListAdapter(mAdapter);

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
//        showProgress(true);
        mSearchTask = new SearchUserTask(query);
        mSearchTask.execute((Void) null);
    }

    /**
     * An asynchronous search user task used find other users
     */
    public class SearchUserTask extends AsyncTask<Void, Void, Boolean> {

        private final String mQuery;

        SearchUserTask(String query) {
            mQuery = query;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Api api = Api.getInstance();

            HashMap<String, String> options = new HashMap<String, String>();
            options.put("command", "search");
            options.put("query", mQuery);

            ApiSearchResult result = api.search(options);

            Log.d("SEARCH", "Search results = " + String.valueOf(result.getResult().size()));

            mList.clear();
            for (SearchResult curVal : result.getResult()) {
                if (curVal.getUsername().toLowerCase().contains(mQuery.toLowerCase())) {
                    mList.add(curVal.getUsername());
                }
            }

            Log.d("SEARCH", "Search: " + mList.toString());

            return mList.size() > 0;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mSearchTask = null;
//            showProgress(false);
            mAdapter.notifyDataSetChanged();
            if (success) {
                setResult(RESULT_OK);
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mSearchTask = null;
//            showProgress(false);
        }
    }
}
