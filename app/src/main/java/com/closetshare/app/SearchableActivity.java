package com.closetshare.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchableActivity extends ListActivity {

    private SearchUserTask mSearchTask = null;

    private ArrayList<String> mList = new ArrayList<String>();

    private ArrayAdapter<String> mAdapter;

    private View mProgressView;

    private View mNoResults;

    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        mProgressView = findViewById(R.id.login_progress);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);
        setListAdapter(mAdapter);

        // view user's closet when clicked
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(SearchableActivity.this, ViewClosetActivity.class);
                i.putExtra("username", mList.get(position));
                i.putExtra("private", false);
                startActivity(i);
            }

        });

        mNoResults = findViewById(R.id.noResults);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(mQuery);
        }
    }

    /**
     * Method where the actual search is done.
     *
     * @param query is the search string.
     */
    private void doMySearch(String query) {
        if (mSearchTask != null) {

            mSearchTask.cancel(true);
        }
        mSearchTask = new SearchUserTask();
        mSearchTask.execute(query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setQuery(mQuery, false);
        searchView.setSubmitButtonEnabled(false);
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    doMySearch(newText);
                }
                return false;
            }
        };
        searchView.setOnQueryTextListener(listener);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * An asynchronous search user task used find other users
     */
    public class SearchUserTask extends AsyncTask<String, Void, ApiSearchResult> {

        String mQuery;

        SearchUserTask() {
        }

        /**
         * Sets up the task by showing the progress bar.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mNoResults.setVisibility(View.GONE);
            showProgress(true);
        }

        /**
         * Retrieves the list of users from the web backend and save entries that contains the
         * query.
         *
         * @param params is the query
         */
        @Override
        protected ApiSearchResult doInBackground(String... params) {
            mQuery = params[0];
            Api api = Api.getInstance();

            HashMap<String, String> options = new HashMap<String, String>();
            options.put("command", "search");
            options.put("query", mQuery);

            return api.search(options);
        }


        @Override
        protected void onPostExecute(ApiSearchResult apiSearchResult) {
            super.onPostExecute(apiSearchResult);

            mList.clear();
            for (SearchResult curVal : apiSearchResult.getResult()) {
                if (isCancelled()) {
                    break;
                }
                if (curVal.getUsername().toLowerCase().contains(mQuery.toLowerCase())) {
                    mList.add(curVal.getUsername());
                }
            }

            if (mList.isEmpty()) {
                mNoResults.setVisibility(View.VISIBLE);
            }

            Log.d("SEARCH", "Search: " + mList.toString());

            mSearchTask = null;
            mAdapter.notifyDataSetChanged();
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mSearchTask = null;
            mAdapter.notifyDataSetChanged();
            showProgress(false);
        }

        /**
         * Shows the progress UI and hides the login form.
         */
        public void showProgress(final boolean show) {
            // we have ViewPropertyAnimator APIs, which allow for very easy animations.
            // Use these APIs to fade-in the progress spinner.
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }

    }
}
