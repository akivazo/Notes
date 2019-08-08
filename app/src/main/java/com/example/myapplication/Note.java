package com.example.myapplication;

public class Note {
    private String note;
    private String comment;
    public Note(){
        note = "";
        comment = "";
    }
    public String getNote(){
        return note;
    }
    public String getComment(){
        return comment;
    }
    public void setNote(String s){
        note = s;
    }
    public void setComment(String s){
        comment = s;
    }
}
