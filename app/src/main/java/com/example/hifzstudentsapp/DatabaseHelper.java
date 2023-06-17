package com.example.hifzstudentsapp;

import android.content.ContentValues;
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
    private static final String COLUMN_SABAQ = "sabaq";
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
                + COLUMN_SABAQ + " TEXT, "
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
    public boolean addStudent(String id, String name, int age, String studentClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID_PRIMARY, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_CLASS, studentClass);
        long result = db.insert(TABLE_STUDENTS, null, values);
        return result != -1;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID_PRIMARY));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int age = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE));
                String className = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS));

                Student student = new Student(id, name, age, className);
                students.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return students;
    }



}
