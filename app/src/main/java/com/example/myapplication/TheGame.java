package com.example.myapplication;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class TheGame extends MainActivity {

    protected static int team = 0;
    protected Note note;
    protected TextView note_view;
    protected String part_str;
    protected int part_num;
    String[] parts = {"שלב ראשון", "שלב שני", "שלב שלישי"};
    protected Button start;
    protected TextView greet;
    protected TextView timer;
    protected TextView text_view;
    protected String[] teams_text = {"קבוצה 1","קבוצה 2"};

    long timeInMillisecond = 15000; // 40 seconds
    private long timeLeftInMillisecond;
    CountDownTimer countDownTimer;
    protected boolean roundOver = false;

    protected enum NoteTarget {USED, UNUSED, THROW };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // disable screen rotation
        setContentView(R.layout.activity_the_game);
        // check what part we are now
        Intent intent = getIntent();
        part_num = intent.getIntExtra("part",0);
        part_str = parts[part_num];
        TextView little = findViewById(R.id.part_little);
        little.setText(part_str);
        start = findViewById(R.id.start);
        timer = findViewById(R.id.timer);
        greet = findViewById(R.id.greet);
        text_view = findViewById(R.id.team);
        initNote();
        showGreet(); // write to the screen the part we are in
        assignDragDropEvents();
    }

    protected void initNote(){
        note = new Note(); // initialize dummy note
        note_view = findViewById(R.id.note);
    }
    protected void showGreet(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                start.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        greet.setText(part_str);
        greet.setVisibility(View.VISIBLE);
        greet.startAnimation(animation);
        greet.setVisibility(View.INVISIBLE);
        text_view.setText(teams_text[team]);
    }
    public void start(View view){
        if (!isVolumeOn(4))
            return;
        start.setVisibility(View.INVISIBLE);
        startTimer();
        updateNewNote(NoteTarget.UNUSED);
    }

    protected void endThisPart(){
        Notes.allToUnused();
        nextTeam(); // change the team in the end of the part
        roundOver = true;
        setResult(PART_ENDED_SUCCESSFULLY);
        summary();
    }
    protected void summary(){
            // maybe will be added in the future
            finish();
    }

    protected void startTimer(){
        //Notes.makeSound(getApplicationContext(), Notes.Sound.timer);
        timeLeftInMillisecond = timeInMillisecond;
        String time = "" + timeInMillisecond;
        timer.setText(time);
        countDownTimer = new CountDownTimer(timeInMillisecond, 1000) {
            @Override
            public void onTick(long left) {
                if (roundOver)
                    this.cancel(); // cancel the timer because we moving to the next part
                timeLeftInMillisecond = left;
                updateTimer();
            }

            @Override
            public void onFinish() {
                Notes.stopSound();
                Notes.makeSound(getApplicationContext(), Notes.Sound.timesUp);
                timer.setText("0");
                timer.setTextColor(Color.RED);
                greet.setVisibility(View.VISIBLE);
                greet.setText("הזמן נגמר");
                greet.setTextColor(Color.RED);

                // wait 5 seconds before moving to the next team
                Handler handler = new Handler();
                handler.postDelayed(() -> nextTeam(),3000);
            }
        }.start();
    }
    // switch team and prepare the screen to the next round. the last note still unused
    protected void nextTeam(){
        team = 1 - team;
        note_view.setText(""); // hide last note from the next team
        text_view.setText(teams_text[team]);
        greet.setVisibility(View.INVISIBLE);
        timer.setTextColor(Color.BLACK);
        start.setVisibility(View.VISIBLE);
    }
    protected void updateTimer(){
        String seconds = "" + (timeLeftInMillisecond / 1000);
        timer.setText(seconds);
    }

    //remove the last note according to the target and update a new one
    protected void updateNewNote(NoteTarget target){
        //removeNoteFromScreen();
        switch (target) {
            case USED:
                Notes.unusedToUsed(note);
                break;
            case THROW:
                Notes.throwNote(note);
                break;
            case UNUSED:
                break; // if the target is unused we dose'nt need do anything because the note is already there
        }
        if (Notes.notesIsEmpty()) {
            endThisPart();
            return; // avoiding any thread from running the next lines and show next note while ending this part
        }
        note = Notes.getRandomNote(); // get a random note
        note_view.setText(note.getNote());
        //setContentView(note_view);
    }
    protected void onSuccess(){
        Notes.addPoint(team);
        updateNewNote(NoteTarget.USED);
    }
    private void assignDragDropEvents(){
        findViewById(R.id.failed).setOnDragListener(new DragEvent(() -> updateNewNote(NoteTarget.USED), note_view));
        findViewById(R.id.success).setOnDragListener(new DragEvent(this::onSuccess, note_view));
        findViewById(R.id.dontKnowReturn).setOnDragListener(new DragEvent(() -> updateNewNote(NoteTarget.UNUSED), note_view));
        findViewById(R.id.dontKnowThrow).setOnDragListener(new DragEvent(() -> updateNewNote(NoteTarget.THROW), note_view));
        setOnTouch();
    }
    protected void setOnTouch(){
        note_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(note_view);

                    note_view.startDrag(data, shadowBuilder, note_view, 0);
                    note_view.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        super.closeOnBack();
    }
    protected void removeNoteFromScreen(){
        ViewGroup parent  = (ViewGroup)note_view.getParent();
        parent.removeView(note_view);
    }
}
