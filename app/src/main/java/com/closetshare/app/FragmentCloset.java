package com.closetshare.app;


import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class FragmentCloset extends Fragment {


    public static ImageAdapter adapter;

    public FragmentCloset() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_closet, container, false);

        GridView mGridView = (GridView) mView.findViewById(R.id.itemGrid);
        adapter = new ImageAdapter(this.getActivity());
        mGridView.setAdapter(adapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(FragmentCloset.this.getActivity(), "View Item" + position,
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), ViewItemActivity.class);
                startActivity(i);
            }
        });

        Button mButton = (Button) mView.findViewById(R.id.addItem);

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(FragmentCloset.this.getActivity(), "Add Item", Toast.LENGTH_SHORT)
                        .show();

                // Display take picture or choose picture dialog
                DialogFragment newFragment = new FragmentDialog();
                newFragment.show(getFragmentManager(), "camera");
            }


        });
        return mView;
    }

}
