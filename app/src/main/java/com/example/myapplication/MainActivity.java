package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.dialogs.PosDialog;
import com.example.myapplication.dialogs.PosNegDialog;


public class MainActivity extends AppCompatActivity {
    final int num_of_parts = 3;
    int part = 0;
    public final int PART_ENDED_SUCCESSFULLY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // disable screen rotation
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true); // if we are already in the main screen, exit
    }

    protected void initGame(){
        part = 0;
        Notes.init();
    }
    @Override
    public boolean onSupportNavigateUp(){
        super.onBackPressed();
        return true;
    }
    public void addNotesText(View view){
        Intent jump = new Intent(getApplicationContext(), AddNotesText.class);
        startActivity(jump);
    }
    public void addNotes(View view){
        Intent jump = new Intent(getApplicationContext(), AddNotes.class);
        startActivity(jump);
    }
    public void startGame(View view){
        Notes.removeEmptyNotes();
        startNextPart();
    }

    protected void startNextPart(){
        Intent jump = new Intent(getApplicationContext(), TheGame.class);
        jump.putExtra("part",part++);
        startActivityForResult(jump, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == PART_ENDED_SUCCESSFULLY)
            if(part == 3)
                declareWinner();
            else
                startNextPart();
    }
    protected void declareWinner(){
        PosDialog winner = new PosDialog("הודעה", Notes.getWinnerString(),"הראה ניקוד",
                this::showPoints);
        super.onPostResume(); // make it possible to show another window
        winner.show(getSupportFragmentManager(),"winner");
    }
    protected void showPoints(){
        PosDialog points = new PosDialog("מידע", Notes.getPointsString(), "אישור",
                this::endGame);
        points.show(getSupportFragmentManager(),"points");
    }
    private void endGame(){
        initGame();
    }
    public void sendNotes(View view){
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        String text = Notes.notesToText();
        if (text.equals("")) {
            Toast.makeText(getApplicationContext(), "אין פתקים לשלוח", Toast.LENGTH_SHORT).show();
            return;
        }
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, text);
        try {

            startActivity(whatsappIntent);

        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(getApplicationContext(),"Whatsap not installed",Toast.LENGTH_SHORT).show();

        }
    }

    public void instruction(View view){
        Intent jump = new Intent(getApplicationContext(), instruction.class);
        startActivity(jump);
    }

    // use for children to close the game if the 'built in back button' is pressed.
    protected void closeOnBack(){
        PosNegDialog dialog = new PosNegDialog("אזהרה", "אם תלחץ על המשך, המשחק יגמר.\nלהמשיך?",
                "המשך","ביטול", ()-> {
                                                        Intent exit = new Intent(getApplicationContext(), MainActivity.class);
                                                        initGame();
                                                        startActivity(exit);
                                                    }
                                                ,()->{});
        dialog.show(getSupportFragmentManager(),"closeOnBack");
    }
    protected void printMessege(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
    // check that the volume is over the minimum volume required
    protected boolean isVolumeOn(int minVal){
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (currentVolume < minVal){
            printMessege("אנא הגבר/י את צלילי המדיה במכשיר שלך");
            return false;
        }
        return true;
    }
    /*
    // fix a bug
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }*/
}
