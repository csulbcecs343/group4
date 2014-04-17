package com.closetshare.app;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class FragmentDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_cam)
                .setItems(R.array.cam_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int option) {

                        Toast.makeText(getActivity(), option + " was clicked", Toast.LENGTH_SHORT)
                                .show();

                        // The 'which' argument contains the index position of the selected item
                        switch (option) {
                            case 0: // Take Photo
                            case 1: // Choose Photo
                                // Start AddItemActivity with the selected option in the intent
                                Intent i = new Intent(getActivity(), AddItemActivity.class);
                                i.putExtra("DialogOption", option);
                                startActivity(i);
                                break;
                            default:
                                break;
                        }
                    }
                });
        return builder.create();
    }

}
