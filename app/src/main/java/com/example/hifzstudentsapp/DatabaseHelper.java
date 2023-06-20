package com.example.hifzstudentsapp;

import android.content.ContentValues;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HifzTeacher.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_STUDENTS = "students";

    // Columns for the 'tasks' table
    private static final String COLUMN_TASK_ID = "task_id";
    private static final String COLUMN_STUDENT_ID = "student_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_SABAQ_PARA = "sabaq_para";
    private static final String COLUMN_SABAQ_VERSE = "sabaq_verse";
    private static final String COLUMN_SABAQ_STATUS = "sabaq_status";
    private static final String COLUMN_SABAQI = "sabaqi";
    private static final String COLUMN_SABAQI_STATUS = "sabaqi_status";
    private static final String COLUMN_MANZIL = "manzil";
    private static final String COLUMN_MANZIL_STATUS = "manzil_status";

    // Columns for the 'students' table
    private static final String COLUMN_STUDENT_ID_PRIMARY = "student_id_primary";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_CLASS = "class";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the 'tasks' table
        String createTasksTableQuery = "CREATE TABLE " + TABLE_TASKS + " ("
                + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_STUDENT_ID + " INTEGER, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_SABAQ_PARA + " TEXT, "
                + COLUMN_SABAQ_VERSE + "TEXT,"
                + COLUMN_SABAQ_STATUS + " TEXT, "
                + COLUMN_SABAQI + " TEXT, "
                + COLUMN_SABAQI_STATUS + " TEXT, "
                + COLUMN_MANZIL + " TEXT, "
                + COLUMN_MANZIL_STATUS + " TEXT)";

        // Create the 'students' table
        String createStudentsTableQuery = "CREATE TABLE " + TABLE_STUDENTS + " ("
                + COLUMN_STUDENT_ID_PRIMARY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_AGE + " INTEGER, "
                + COLUMN_CLASS + " TEXT)";

        db.execSQL(createTasksTableQuery);
        db.execSQL(createStudentsTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }



    public boolean addStudent( String name, int age, String studentClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_CLASS, studentClass);
        long result = db.insert(TABLE_STUDENTS, null, values);
        db.close();
        return result != -1;

    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID_PRIMARY));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") int age = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE));
                @SuppressLint("Range") String className = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS));

                Student student = new Student(id, name, age, className);
                students.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return students;
    }
    public boolean updateStudent(int studentId, String newName, int newAge, String newClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_AGE, newAge);
        values.put(COLUMN_CLASS, newClass);

        int rowsAffected = db.update(TABLE_STUDENTS, values, COLUMN_STUDENT_ID_PRIMARY + " = ?",
                new String[]{String.valueOf(studentId)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean deleteStudent(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int rowsAffected = db.delete(TABLE_STUDENTS, COLUMN_STUDENT_ID_PRIMARY + " = ?",
                new String[]{String.valueOf(studentId)});

        return rowsAffected > 0;
    }
    public Student getStudentById(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_STUDENT_ID_PRIMARY,
                COLUMN_NAME,
                COLUMN_AGE,
                COLUMN_CLASS
        };

        String selection = COLUMN_STUDENT_ID_PRIMARY + " = ?";
        String[] selectionArgs = {String.valueOf(studentId)};

        Cursor cursor = db.query(
                TABLE_STUDENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Student student = null;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range")int id = cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID_PRIMARY));
            @SuppressLint("Range")String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range")int age = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE));
            @SuppressLint("Range")String className = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS));

            student = new Student(id, name, age, className);
        }

        cursor.close();
        return student;
    }



}
