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
    private void saveNote(){
        EditText note_view = findViewById(R.id.note);
        EditText comment_view = findViewById(R.id.comment);
        String note = note_view.getText().toString();
        String comment = comment_view.getText().toString();
        notes.add(new Note(note, comment));
    }
    public void  nextNote(View view){
        saveNote();
        Intent next = new Intent(getApplicationContext(), AddNotes.class);
        startActivity(next);
    }
    public void finish(View view){
        saveNote();
        Intent finish = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(finish);
    }
}
