package com.example.hifzstudentsapp;

public class Task {
    private int taskId;
    private int studentId;
    private String date;
    private String sabaqPara;
    private String sabaqSurah;
    private String sabaqVerse;
    private boolean sabaqStatus;
    private String manzilPara;
    private boolean manzilStatus;
    private String sabaqiPara;
    private boolean sabaqiStatus;

    public Task() {
        // Default constructor
    }
    public Task(int taskId, int studentId, String date, String sabaqPara,String sabaqSurah,String sabaqVerse, boolean sabaqStatus,String manzilPara, boolean manzilStatus, String sabaqiPara, boolean sabaqiStatus) {
        this.taskId = taskId;
        this.studentId = studentId;
        this.date = date;
        this.sabaqPara = sabaqPara;
        this.sabaqSurah = sabaqSurah;
        this.sabaqVerse = sabaqVerse;
        this.sabaqStatus = sabaqStatus;
        this.manzilPara = manzilPara;
        this.manzilStatus = manzilStatus;
        this.sabaqiPara = sabaqiPara;
        this.sabaqiStatus = sabaqiStatus;
    }
    public Task(int taskId, int studentId, String date, String sabaqPara,String sabaqSurah,String sabaqVerse,String manzilPara,  String sabaqiPara) {
        this.taskId = taskId;
        this.studentId = studentId;
        this.date = date;
        this.sabaqPara = sabaqPara;
        this.sabaqSurah=sabaqSurah;
        this.sabaqVerse=sabaqVerse;
        this.manzilPara = manzilPara;

        this.sabaqiPara = sabaqiPara;

    }
public int getStudentId(){return studentId;}
    public  int getTaskId(){return taskId;}
    public String getSabaqPara() {
        return sabaqPara;
    }
    public String date(){return date;}
    public String getSabaqSurah() {
        return sabaqSurah;
    }
    public String getSabaqVerse() {
        return sabaqVerse;
    }


    public boolean getSabaqStatus() {
        return sabaqStatus;
    }

    public String getManzilPara() {
        return manzilPara;
    }

    public boolean getManzilStatus() {
        return manzilStatus;
    }

    public String getSabaqiPara() {
        return sabaqiPara;
    }

    public boolean getSabaqiStatus() {
        return sabaqiStatus;
    }

    public void setSabaqStatus(boolean sabaqStatus) {
        this.sabaqStatus = sabaqStatus;
    }
    public void setManzilStatus(boolean manzilStatus) {
        this.manzilStatus = manzilStatus;
    }
    public void setSabaqiStatus(boolean sabaqiStatus) {
        this.sabaqiStatus = sabaqiStatus;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setSabaqPara(String sabaqPara) {
        this.sabaqPara = sabaqPara;
    }

    public void setSabaqSurah(String sabaqSurah) {
        this.sabaqSurah = sabaqSurah;
    }
    public void setSabaqVerse(String sabaqVerse) {
        this.sabaqVerse = sabaqVerse;
    }
    public void setManzilPara(String manzilPara) {
        this.manzilPara = manzilPara;
    }
    public void setSabaqiPara(String sabaqiPara) {
        this.sabaqiPara = sabaqiPara;
    }

    // Other methods and getter/setter for remaining attributes
}


