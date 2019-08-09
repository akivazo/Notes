package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.dialogs.PosDialog;
import com.example.myapplication.dialogs.PosNegDialog;

import java.util.LinkedList;

import static java.lang.System.exit;


public class MainActivity extends AppCompatActivity {
    protected LinkedList<Note> notes = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true); // if we are already in the main screen, exit
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
        Intent jump = new Intent(getApplicationContext(), AddNotesText.class);
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
        PosNegDialog dialog = new PosNegDialog("warning", "אם תלחץ על המשך, המשחק יגמר.\nלהמשיך?",
                "המשך","ביטול", ()-> {
                                                        Intent exit = new Intent(getApplicationContext(), MainActivity.class);
                                                        initGame();
                                                        startActivity(exit);
                                                    }
                                                ,()->{});
        dialog.show(getSupportFragmentManager(),"closeOnBack");
    }
    protected void errorMessege(String msg, Runnable func, String tag){
        PosDialog error = new PosDialog("error", msg, "אישור", func);
        error.show(getSupportFragmentManager(), tag);
    }
}
