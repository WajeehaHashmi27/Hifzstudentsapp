package com.example.hifzstudentsapp;

public class Student {
    private int studentId;
    private String name;
    private int age;
    private String className;

    public Student() {
        // Default constructor
    }

    public Student(String name, int age, String className) {
        this.name = name;
        this.age = age;
        this.className = className;
    }
    public Student(int id, String name, int age, String className) {
        this.studentId = id;
        this.name = name;
        this.age = age;
        this.className = className;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

