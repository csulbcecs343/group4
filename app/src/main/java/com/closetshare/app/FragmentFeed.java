package com.closetshare.app;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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

        for (int i = 0; i < 24; i++) {

            postAdapter.addItem(("User " + i),
                    ("http://build.vibrantdavee.com/testimg/" + i % 4 + ".jpg"),
                    i % 2 == 0 ? ("I am User " + i + " check me out!")
                            : ("I am \nUser " + i + "\n check me out!"),
                    i * 5
            );
        }

        // credit: http://sriramramani.wordpress.com/2012/10/17/instagram-list-view/
        // OnScrollListener is the AbsListView.OnScrollListener.
        view.setOnScrollListener(new AbsListView.OnScrollListener() {
            int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // Store the state to avoid re-laying out in IDLE state.
                mScrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView list, int firstItem, int visibleCount,
                    int totalCount) {
                // Nothing to do in IDLE state.
                if (mScrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }

                for (int i = 0; i < visibleCount; i++) {
                    View listItem = list.getChildAt(i);
                    if (listItem == null) {
                        break;
                    }

                    View title = listItem.findViewById(R.id.title);

                    int topMargin = 0;
                    if (i == 0) {
                        int top = listItem.getTop();
                        int height = listItem.getHeight();

                        if (top < 0) {
                            // if top is negative, the list item has scrolled up.
                            if (title.getHeight() < (top + height)) {
                                // if the title view falls within the container's visible portion,
                                //     set the top margin to be the (inverse) scrolled amount of the container.
                                topMargin = -top;

                            } else {
                                // else
                                //     set the top margin to be the difference between the heights.
                                topMargin = (height - title.getHeight());
                            }
                        }
                    }

                    // set the offset with setTranslationY for smoother transition
                    title.setTranslationY(topMargin);
                }
            }
        });

        return mView;
    }


}
