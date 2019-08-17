package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNotes extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
    }
    @Override
    public void onBackPressed(){
        super.closeOnBack();
    }
    // add the note to the notes list in the base class
    private void saveNote(){
        EditText note_view = findViewById(R.id.note);
        EditText comment_view = findViewById(R.id.comment);
        String note = note_view.getText().toString();
        String comment = comment_view.getText().toString();
        Notes.addNote(new Note(note, comment));
    }
    // save the current note and create a new screen
    public void nextNote(View view){
        saveNote();
        EditText note_view = findViewById(R.id.note);
        EditText comment_view = findViewById(R.id.comment);
        note_view.setText("");
        comment_view.setText("");
    }
    // return to main screen
    public void finish(View view){
        //saveNote();
        finish();
    }
}
