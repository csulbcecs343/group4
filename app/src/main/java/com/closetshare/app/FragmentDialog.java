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
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0: {
                                Toast.makeText(FragmentDialog.this.getActivity(), which + " was clicked", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getActivity(), AddItemActivity.class);
                                startActivity(i);
                                break;
                            }
                            case 1: {
                                Toast.makeText(FragmentDialog.this.getActivity(), which + " was clicked", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getActivity(), AddItemActivity.class);
                                startActivity(i);
                                break;
                            }
                            default: {
                                Toast.makeText(FragmentDialog.this.getActivity(), which + " was clicked", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getActivity(), AddItemActivity.class);
                                startActivity(i);
                                break;
                            }
                        }
                    }
                });
        return builder.create();
    }


}
