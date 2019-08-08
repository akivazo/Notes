package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;

import static java.lang.System.exit;


public class MainActivity extends AppCompatActivity {
    protected LinkedList<Note> notes = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // check if the user exit the app in other screen
        if (getIntent().getBooleanExtra("EXIT", false))
        {
            moveTaskToBack(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        finish(); // if we are already in the main screen, exit
    }

    protected void initGame(){
        notes = new LinkedList<>();
    }
    @Override
    public boolean onSupportNavigateUp(){
        super.onBackPressed();
        return true;
    }
    public void addNotes(View view){
        Intent jump = new Intent(getApplicationContext(), addNotes.class);
        startActivity(jump);
    }
    public void startGame(View view){
        Intent jump = new Intent(getApplicationContext(), startGame.class);
        startActivity(jump);
    }
    public void instruction(View view){
        Intent jump = new Intent(getApplicationContext(), instruction.class);
        startActivity(jump);
    }
    // use for children to close the game if the 'built in back button' is pressed.
    protected void closeOnBack(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("warning");
        dialog.setMessage("אם תלחץ על המשך, המשחק יגמר.\nלהמשיך?");
        dialog.setPositiveButton("המשך", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent exit = new Intent(getApplicationContext(), MainActivity.class);
                initGame();
                startActivity(exit);
            }
        });
        dialog.setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { }
        });
        dialog.create().show();
    }
}
