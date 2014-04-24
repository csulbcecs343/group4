package com.closetshare.app;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    // TODO change to use result from database instead of urls and uris
    public List<String> urls = new ArrayList<String>() {{
    }};

    public List<Uri> uris = new ArrayList<Uri>();

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public void addItem(String item) {
        urls.add(item);
        notifyDataSetChanged();
    }

    public void addItem(Uri item) {
        uris.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        String message = "Pos: " + position +
                "\nurls size: " + urls.size() +
                "\nuris size: " + uris.size();
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

        // Get the image URL for the current position.
        if (isURI(position)) {
            uris.remove(position - urls.size());
        } else {
            urls.remove(position);
        }
        notifyDataSetChanged();
    }

    public boolean isURI(int position) {
        return (position >= urls.size());
    }

    public int getCount() {
        return urls.size() + uris.size();
    }

    public String getItem(int position) {
        return urls.get(position);
    }

    public Uri getURIItem(int position) {
        return uris.get(position - urls.size());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView imageView = (SquaredImageView) convertView;

        if (imageView == null) {
            imageView = new SquaredImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        // Get the image URL for the current position.
        if (position < urls.size()) {
            String url = getItem(position);

            // Trigger the download of the URL asynchronously into the image view.
            Picasso.with(mContext)
                    .load(url)
                    .into(imageView);
        } else {
            Uri uri = getURIItem(position);

            // Trigger the download of the URL asynchronously into the image view.
            Picasso.with(mContext)
                    .load(uri)
                    .into(imageView);
        }

        return imageView;
    }

}
