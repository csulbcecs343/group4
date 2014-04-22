package com.closetshare.app;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class FragmentFeed extends Fragment {


    public FragmentFeed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_feed, container, false);

        PostAdapter postAdapter = new PostAdapter(getActivity());
        ListView view = (ListView) mView.findViewById(R.id.listView);
        view.setAdapter(postAdapter);

        for (int i = 0; i < 4; i++) {

            postAdapter.addItem(("User " + i),
                    ("http://build.vibrantdavee.com/testimg/" + i + ".jpg"),
                    ("I am User " + i + " check me out!"),
                    i * 5);
        }

        return mView;
    }


}
