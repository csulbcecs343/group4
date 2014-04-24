package com.closetshare.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class ViewClosetActivity extends Activity {

    public static ImageAdapter adapter;

    private String mUsername;

    private Boolean mPrivate;

    private TextView mPostVal;

    private ViewClosetTask mTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_closet);

        Intent i = getIntent();
        Bundle mBundle = i.getExtras();

        if (mBundle != null) {
            mUsername = mBundle.getString("username");
            mPrivate = mBundle.getBoolean("private");
        }

        mPostVal = (TextView) findViewById(R.id.postCount);

        GridView mGridView = (GridView) findViewById(R.id.itemGrid);
        adapter = new ImageAdapter(this);
        mGridView.setAdapter(adapter);

        mTask = new ViewClosetTask();
        mTask.execute();

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(ViewClosetActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
                // view item when picture is clicked
                Intent i = new Intent(ViewClosetActivity.this, ViewItemActivity.class);
                i.putExtra("fragment", "viewcloset");
                i.putExtra("PicPos", position);
                startActivity(i);
            }
        });

        setTitle(mUsername + "'s Closet");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_closet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * An asynchronous pull of a user's closet
     */
    class ViewClosetTask extends AsyncTask<Void, Void, ApiViewCloset> {

        public ViewClosetTask() {
            super();
        }

        @Override
        protected ApiViewCloset doInBackground(Void... params) {
            Api api = Api.getInstance();

            HashMap<String, String> options = new HashMap<String, String>();
            options.put("command", "view_closet");
            options.put("username", mUsername);
            options.put("private", mPrivate ? "1" : "0");

            return api.viewCloset(options);
        }

        @Override
        protected void onPostExecute(ApiViewCloset apiViewCloset) {
            super.onPostExecute(apiViewCloset);

            for (ClosetItem curVal : apiViewCloset.getResult()) {
                if (isCancelled()) {
                    break;
                }

                adapter.addItem(
                        "http://build.vibrantdavee.com/testimg/" + curVal.getPhotoId() + ".jpg");
            }

            mPostVal.setText(String.valueOf(adapter.getCount()));
        }
    }
}
