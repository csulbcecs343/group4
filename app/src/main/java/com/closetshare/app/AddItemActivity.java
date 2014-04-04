package com.closetshare.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class AddItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        ImageView mImageView = (ImageView) findViewById(R.id.photo);
        LayoutParams params  = mImageView.getLayoutParams();
        params.height = 750;
        params.width  = 750;
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        EditText mDescription = (EditText) findViewById(R.id.description);
        EditText mTags = (EditText) findViewById(R.id.tags);

        Button mButton = (Button) findViewById(R.id.addButton);


        mButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(AddItemActivity.this, "Add Item", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_item, menu);
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
