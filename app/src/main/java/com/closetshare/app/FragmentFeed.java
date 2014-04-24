package com.closetshare.app;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.HashMap;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class FragmentFeed extends Fragment {

    GetFeedTask mTask = null;

    PostAdapter mAdapter;


    public FragmentFeed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_feed, container, false);

        mAdapter = new PostAdapter(getActivity());
        ListView view = (ListView) mView.findViewById(R.id.listView);
        view.setAdapter(mAdapter);

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

        mTask = new GetFeedTask();
        mTask.execute();

        return mView;
    }

    /**
     * An asynchronous pull of a user's closet
     */
    class GetFeedTask extends AsyncTask<Void, Void, ApiViewCloset> {

        public GetFeedTask() {
            super();
        }

        @Override
        protected ApiViewCloset doInBackground(Void... params) {
            Api api = Api.getInstance();

            HashMap<String, String> options = new HashMap<String, String>();
            options.put("command", "stream");

            return api.viewCloset(options);
        }

        @Override
        protected void onPostExecute(ApiViewCloset apiViewCloset) {
            super.onPostExecute(apiViewCloset);

            for (ClosetItem curVal : apiViewCloset.getResult()) {
                if (isCancelled()) {
                    break;
                }

                mAdapter.addItem((curVal.getUsername()),
                        ("http://build.vibrantdavee.com/testimg/" + curVal.getPhotoId() + ".jpg"),
                        curVal.getDescript(),
                        Integer.parseInt(curVal.getUserId()) * 5
                );
            }
        }
    }

}
