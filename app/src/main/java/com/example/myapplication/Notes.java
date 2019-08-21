package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.LinkedList;
import java.util.Random;

// global class for global variable and methods
public class Notes extends Application {
    private static LinkedList<Note> unused_notes; // play as global variable
    private static LinkedList<Note> used_notes; // play as global variable
    protected static int[] teamsPoints;

    private static SoundPool soundPool;
    public enum Sound {timesUp, timer} // used in makeSound function to choose the sound to play
    public Notes(){
        unused_notes =  new LinkedList<Note>();
        used_notes =  new LinkedList<Note>();
        teamsPoints = new int[]{0,0};
        loadSounds();
    }
    public static void init(){
        unused_notes =  new LinkedList<Note>();
        used_notes =  new LinkedList<Note>();
        teamsPoints = new int[]{0,0};
    }
    public static void removeEmptyNotes(){
        for ( Note note : unused_notes){
            if (note.getFigure().equals(""))
                unused_notes.remove(note);
        }
    }
    public static void addPoint(int team){
        teamsPoints[team]++;
    }
    public static void addNotes(LinkedList<Note> notes){
        unused_notes.addAll(notes);
    }
    public static void addNote(Note note){
        unused_notes.add(note);
    }
    //return a random note
    public static Note getRandomNote(){
        Random rand = new Random();
        //rand.nextInt(unused_notes.size())
        return unused_notes.get(0);
    }
    protected static String notesToText(){
        removeEmptyNotes();
        if (notesIsEmpty())
            return "";
        StringBuilder text = new StringBuilder();
        String temp = "";
        for ( Note note : unused_notes ){
            temp = note.getFigure() + note.getComment() + ",";
            text = text.append(temp);
        }
        return text.substring(0, text.length() - 1); // to remove the ',' in the end
    }
    public static void unusedToUsed(Note note){
        unused_notes.remove(note);
        used_notes.add(note);
    }
    public static void throwNote(Note note){
        unused_notes.remove(note);
    }
    // change the used notes to unused
    public static void allToUnused(){
        unused_notes.addAll(used_notes);
        used_notes.clear();
    }
    //return string of the winner in the game
    public static String getWinnerString(){
        if (teamsPoints[0] > teamsPoints[1])
            return "קבוצה 1 ניצחה!";
        else if(teamsPoints[1] > teamsPoints[0])
            return "קבוצה 2 ניצחה!";
        else
            return "תיקו!";
    }
    public static String getPointsString(){
        String team1 = "קבוצה 1: " + teamsPoints[0];
        String team2 = "קבוצה 2: " + teamsPoints[1];
        return team1 + "\n" + team2;
    }
    // return true if unused_note is empty
    public static boolean notesIsEmpty(){
        return unused_notes.isEmpty();
    }

    private void loadSounds(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            soundPool = new SoundPool.Builder().setMaxStreams(6).setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }

    }
    public static void makeSound(Context context, Sound sound){
        switch (sound){
            case timesUp:
                int elevator = soundPool.load(context,R.raw.elevator,1);
                // play the sound right after it been loaded
                soundPool.setOnLoadCompleteListener((SoundPool soundpool, int sampleId, int status)
                        -> { if (sampleId == elevator && status == 0)
                    soundPool.play(elevator, 1, 1, 0, 0, 1);});
                break;
            case timer:
                int alarm_tick = soundPool.load(context,R.raw.alarm_tick,1);
                // play the sound right after it been loaded
                soundPool.setOnLoadCompleteListener((SoundPool soundpool, int sampleId, int status)
                        -> { if (sampleId == R.raw.alarm_tick && status == 0)
                    soundPool.play(alarm_tick, 1, 1, 0, -1, 1);});
                break;
        }
    }
    public static void stopSound(){
        soundPool.autoPause();
    }
}
