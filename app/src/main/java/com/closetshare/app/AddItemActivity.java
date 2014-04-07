package com.closetshare.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddItemActivity extends Activity {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 300;
    private static final String APP_TAG = "ClosetShare";
    EditText mDescription;
    EditText mTags;
    ImageView mImageView;
    private Uri fileUri;

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), APP_TAG);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(APP_TAG, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        if (b != null) {
            int option = b.getInt("DialogOption");
            Toast.makeText(this, "option: " + option, Toast.LENGTH_LONG).show();
            getPhoto(option);
        }

        mImageView = (ImageView) findViewById(R.id.photo);
        LayoutParams params = mImageView.getLayoutParams();
        params.height = 750;
        params.width = 750;
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mDescription = (EditText) findViewById(R.id.description);
        mTags = (EditText) findViewById(R.id.tags);

        Button mButton = (Button) findViewById(R.id.addButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addItem(v);
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

    public void addItem(View v) {
        String mToast = "Added Item!\n" +
                "Description: " + mDescription.getText() + "\n" +
                "Tags: " + mTags.getText() + "\n" +
                "Path: " + fileUri.getPath();
        Toast.makeText(AddItemActivity.this, mToast, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void getPhoto(int which) {
        switch (which) {
            case 0: {
                // Take Photo
                // Credit: http://developer.android.com/guide/topics/media/camera.html
                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                break;
            }
            case 1: {
                // Choose Photo
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        GALLERY_REQUEST_CODE);
                break;
            }
            default: {
                // Do nothing
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                // Credit: http://stackoverflow.com/questions/8997050/android-crashing-after-camera-intent
                // Decode it for real
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = false;

                //imageFilePath image path which you pass with intent
                Bitmap bmp = BitmapFactory.decodeFile(fileUri.getPath(), bmpFactoryOptions);

                mImageView.setImageBitmap(bmp);
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                // Credit: https://github.com/thecodepath/android_guides/wiki/Using-Hardware,-Sensors-and-Device-data
                // Selected image saved to fileUri specified in the Intent
                fileUri = data.getData();

                Toast.makeText(this, "Selected image saved at:\n" +
                        fileUri, Toast.LENGTH_LONG).show();

                // Do something with the photo based on Uri
                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Load the selected image into a preview
                mImageView.setImageBitmap(bmp);
            }
        }
    }
}
