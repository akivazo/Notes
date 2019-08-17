package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Note extends AppCompatActivity{
    private String figure;
    private String comment;
    public Note(String note, String comment){
        this.figure = note;
        if (comment.equals(""))
            this.comment = "";
        else
            this.comment = "(" + comment + ")";
    }
    public Note(){
        this.figure = "";
        this.comment = "";
    }
    public String getFigure(){
        return figure;
    }
    public String getComment(){
        return comment;
    }
    public void setFigure(String s){
        figure = s;
    }
    public void setComment(String s){
        comment = s;
    }
    public TextView show(Activity activity){
        TextView note = new TextView(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(500,500);
        params.gravity = Gravity.CENTER;
        params.setMargins(40,40,40,250);
        String allNote = this.figure + "\n" + this.comment;
        note.setText(allNote);
        //note.setLayoutParams(params);
        note.setTextSize(30);
        note.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        activity.addContentView(note,params);
        return note;
    }
    public String getNote(){
        return this.figure + "\n" + this.comment;
    }
}
