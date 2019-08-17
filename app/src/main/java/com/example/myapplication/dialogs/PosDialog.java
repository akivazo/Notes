package com.example.myapplication.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

//class represent a dialog window with positive button

public class PosDialog extends AppCompatDialogFragment {
    private String title;
    private String message;
    private String posBtn;
    private Runnable posfun;

    /**
     * after created this class to show the window write: "dialog_name.show(getSupportFragmentManager(),"<some tag>"); "
      * @param title: the title of the dialog window
     * @param messege: the message the dialog window
     * @param posBtn: the name the will show on the positive button
     * @param posfun: the function to run on click on the button
     */
    public PosDialog(String title, String messege, String posBtn, Runnable posfun){
        this.title = title;
        this.message = messege;
        this.posBtn = posBtn;
        this.posfun = posfun;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton(posBtn, (DialogInterface dialogInterface, int i) -> posfun.run());

        return dialog.create();
    }
}

