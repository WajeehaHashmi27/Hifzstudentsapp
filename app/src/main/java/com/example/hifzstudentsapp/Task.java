package com.example.hifzstudentsapp;

public class Task {
    private int taskId;
    private int studentId;
    private String date;
    private Sabaq sabaq;
    private boolean sabaqStatus;
    private Manzil manzil;
    private boolean manzilStatus;
    private Sabaqi sabaqi;
    private boolean sabaqiStatus;


    public Task(int taskId, int studentId, String date, Sabaq sabaq, boolean sabaqStatus, Manzil manzil, boolean manzilStatus, Sabaqi sabaqi, boolean sabaqiStatus) {
        this.taskId = taskId;
        this.studentId = studentId;
        this.date = date;
        this.sabaq = sabaq;
        this.sabaqStatus = sabaqStatus;
        this.manzil = manzil;
        this.manzilStatus = manzilStatus;
        this.sabaqi = sabaqi;
        this.sabaqiStatus = sabaqiStatus;
    }
public int getStudentId(){return studentId;}
    public  int getTaskId(){return taskId;}
    public Sabaq getSabaq() {
        return sabaq;
    }
    public String date(){return date;}


    public boolean getSabaqStatus() {
        return sabaqStatus;
    }

    public Manzil getManzil() {
        return manzil;
    }

    public boolean getManzilStatus() {
        return manzilStatus;
    }

    public Sabaqi getSabaqi() {
        return sabaqi;
    }

    public boolean getSabaqiStatus() {
        return sabaqiStatus;
    }
    public String getSabaqPara(){return sabaq.sabaqPara;}
    public String getSabaqiPara(){return sabaqi.sabaqiPara;}
    public String getSabaqVerse(){return sabaq.sabaqVerse;}
    public String getManzilPara(){return manzil.manzilPara;}
    public String getSabaqSurah(){return sabaq.sabaqSurah;}
    // Other methods and getter/setter for remaining attributes
}


