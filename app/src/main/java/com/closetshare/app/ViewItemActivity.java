package com.closetshare.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.squareup.picasso.Picasso;


public class ViewItemActivity extends Activity {

    int mPosition;

    ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        Intent i = getIntent();
        Bundle mBundle = i.getExtras();

        SquaredImageView imageView = (SquaredImageView) findViewById(R.id.photo);

        if (mBundle != null) {
            mPosition = mBundle.getInt("PicPos");
            String mFragment = mBundle.getString("fragment");

            assert mFragment != null;
            if (mFragment.equals("closet")) {
                mAdapter = FragmentCloset.adapter;
            } else if (mFragment.equals("explore")) {
                mAdapter = FragmentExplore.adapter;
            }

            if (mAdapter.isURI(mPosition)) {
                Picasso.with(this).load(mAdapter.getURIItem(mPosition)).into(imageView);
            } else {
                Picasso.with(this).load(mAdapter.getItem(mPosition)).into(imageView);
            }
        }

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAdapter.removeItem(mPosition);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_item_activtiy, menu);
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

}
