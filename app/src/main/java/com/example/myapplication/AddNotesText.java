package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.android.material.textfield.TextInputLayout;


import java.util.LinkedList;


// new screen to get notes from the user
public class AddNotesText extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // disable screen rotation
        setContentView(R.layout.activity_add_notes_text);
        // display return button in the toolbar (in the top of the screen)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("הוסף פתקים");
    }
    @Override
    public void onBackPressed(){
        super.closeOnBack();
    }

    // save the notes that been entered so far.
    public void saveNotes(View view){
        TextInputLayout inputView = findViewById(R.id.input);
        String text = inputView.getEditText().getText().toString();
        try {
            Notes.addNotes(_getNotesFromText(text));
        }catch (InvalidInput e){
            printMessege("לא סגרת את הסוגריים כמו שצריך.\nהסתכל/י בפורמט למעלה כדי לתקן.");// show error if there is'nt a closing parenthesis in the end
            return;
        }
        // jump back to the main screen
        Intent jump = new Intent(getApplicationContext(), MainActivity.class);
        Button save = findViewById(R.id.save);
        save.setVisibility(View.INVISIBLE);
        startActivity(jump);
    }


    // parse the text input from the user, into list.
    private LinkedList<Note> _getNotesFromText(String text) throws InvalidInput{
        LinkedList<Note> notes = new LinkedList<>();
        int i = 0, j = 0; // i hold the start of the note. j find his end.
        int len = text.length();
        while(j < len){
            // if we reach end of a note or the end of the text
            if ( j == len - 1 || text.charAt(j + 1) == ',' ){
                // parse the text to Note object
                 notes.addFirst(_getNote(text.substring(i,j + 1)));
                 i = j + 2; // i point to the next note's start
            }
            j++;
        }
        return notes;
    }

    //parse a single text note. separate the note itself and the comment.
    private Note _getNote(String text) throws InvalidInput{
        Note note = new Note();
        int i = 0;
        int len = text.length();
        while(i < len){
            if (text.charAt(i) == '('){
                if (text.charAt(len-1) != ')') {
                    throw new InvalidInput();
                }
                note.setComment(text.substring(i+1, len - 1)); // set the text in the parenthesis as comment
                break;
            }
            i++;
        }
        note.setFigure(text.substring(0, i));
        return note;
    }

    private class InvalidInput extends Throwable{ }

}
