package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddNotes extends MainActivity {
    TextView note_view;
    TextView comment_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // disable screen rotation
        setContentView(R.layout.activity_add_notes);
        note_view = findViewById(R.id.note);
        comment_view = findViewById(R.id.comment);
    }
    @Override
    public void onBackPressed(){
        super.closeOnBack();
    }
    // add the note to the notes list in the base class
    private boolean saveNote(){
        String note = note_view.getText().toString();
        String comment = comment_view.getText().toString();
        if (note.contains(",") || comment.contains(",")){
            printMessege("אין להשתמש בתו ' , '");
            return false; // the note did not been accepted
        }
        Notes.addNote(new Note(note, comment));
        return true;

    }
    // save the current note and create a new screen
    public void nextNote(View view){
        if (!saveNote()) // if the note did'nt saved don't delete it
            return;
        note_view.setText("");
        comment_view.setText("");
    }
    // return to main screen
    public void finish(View view){
        //saveNote();
        finish();
    }
}
