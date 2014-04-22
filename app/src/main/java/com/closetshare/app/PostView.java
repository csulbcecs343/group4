package com.closetshare.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by vibrantdavee on 4/21/14.
 */
public class PostView extends LinearLayout {

    private Context mContext;

    private String mUsername;

    public PostView(Context context) {
        super(context);

        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.post_view, this);
    }

    public PostView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.post_view, this);
    }

    public void setData(String username, String photoUrl, String description, int rating) {
        setUsername(username);
        setDescription(description);
        setPhoto(photoUrl);
        setRating(rating);
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
        TextView textView = (TextView) findViewById(R.id.username);
        textView.setText(mUsername);
    }

    public void setDescription(String description) {
        TextView textView = (TextView) findViewById(R.id.description);

        textView.setText(description);
    }

    public void setPhoto(String photo) {
        SquaredImageView imageView = (SquaredImageView) findViewById(R.id.photo);
        Picasso.with(mContext).load(photo).into(imageView);
    }

    public void setRating(int rating) {
        TextView textView = (TextView) findViewById(R.id.rating);

        textView.setText(rating + " likes");
    }
}
