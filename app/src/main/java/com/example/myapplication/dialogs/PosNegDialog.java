package com.example.myapplication.dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.io.PipedOutputStream;
import java.util.function.Consumer;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.MainActivity;

//class represent a dialog window with positive and negative button
// after create this class to show the window write: " dialog_name.show(getSupportFragmentManager(),"<some tag>"); "
public class PosNegDialog extends AppCompatDialogFragment {
    private String title;
    private String messege;
    private String posBtn;
    private String negBtn;
    private Runnable posfun;
    private Runnable negfun;

    /**
     *
     * @param title: the title of the dialog window
     * @param messege: the message the dialog window
     * @param posBtn: the name the will show on the positive button
     * @param negBtn: the name the will show on the negative button
     * @param posfun: the function to be run on click on the positive button
     * @param negfun: the function to be run on click on the negative button
     */
    public PosNegDialog(String title, String messege, String posBtn, String negBtn, Runnable posfun, Runnable negfun){
        this.title = title;
        this.messege = messege;
        this.posBtn = posBtn;
        this.negBtn = negBtn;
        this.posfun = posfun;
        this.negfun = negfun;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(messege);
        dialog.setPositiveButton(posBtn, (DialogInterface dialogInterface, int i) -> posfun.run());
        dialog.setNegativeButton(negBtn, (DialogInterface dialogInterface, int i) -> negfun.run());
        return dialog.create();
    }
}
