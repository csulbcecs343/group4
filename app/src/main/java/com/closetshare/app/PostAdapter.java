package com.closetshare.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

    Context mContext;

    ArrayList<PostView> mList = new ArrayList<PostView>();


    public PostAdapter(Context context) {
        mContext = context;
    }

    public void addItem(String username, String photoUrl, String description, int rating) {
        PostView postView = new PostView(mContext);
        postView.setData(username, photoUrl, description, rating);
        mList.add(postView);

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PostView getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostView postView = (PostView) convertView;

        if (postView == null) {
            postView = new PostView(mContext);
        } else {
            postView = mList.get(position);
        }

        return postView;
    }
}
