package com.closetshare.app;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class FragmentCloset extends Fragment {


    public FragmentCloset() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_closet, container, false);
    }


}
