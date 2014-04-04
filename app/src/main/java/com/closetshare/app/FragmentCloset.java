package com.closetshare.app;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


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
        View mView = inflater.inflate(R.layout.fragment_closet, container, false);


        GridView gridview = (GridView) mView.findViewById(R.id.itemGrid);
        gridview.setAdapter(new ImageAdapter(this.getActivity()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(FragmentCloset.this.getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        return mView;
    }


}