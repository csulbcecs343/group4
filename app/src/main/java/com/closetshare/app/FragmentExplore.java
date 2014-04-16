package com.closetshare.app;



import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class FragmentExplore extends Fragment {


    public FragmentExplore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_explore, container, false);


        GridView mGridView = (GridView) mView.findViewById(R.id.itemGrid);
        mGridView.setAdapter(new ImageAdapter(this.getActivity()));

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(FragmentExplore.this.getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), ViewItemActivity.class);
                startActivity(i);
            }

        });
        return mView;
    }


}
