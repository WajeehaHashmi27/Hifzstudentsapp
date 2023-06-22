package com.example.hifzstudentsapp;

import android.content.ContentValues;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.util.Log;

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
    private static final String COLUMN_SABAQ_SURAH = "sabaq_surah";
    private static final String COLUMN_SABAQ_VERSE = "sabaq_verse";

    private static final String COLUMN_SABAQ_STATUS = "sabaq_status";
    private static final String COLUMN_SABAQI_PARA = "sabaqi_para";
    private static final String COLUMN_SABAQI_STATUS = "sabaqi_status";
    private static final String COLUMN_MANZIL_PARA = "manzil_para";
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
                + COLUMN_SABAQ_SURAH + " TEXT, "
                + COLUMN_SABAQ_VERSE + " TEXT, "
                + COLUMN_SABAQ_STATUS + " TEXT, "
                + COLUMN_SABAQI_PARA + " TEXT, "
                + COLUMN_SABAQI_STATUS + " TEXT, "
                + COLUMN_MANZIL_PARA + " TEXT, "
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
    public List<Task> getTasksByStudentIdAndTaskId(int studentId) {
        List<Task> tasks = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TASKS + " WHERE "
                + COLUMN_STUDENT_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(studentId)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")int taskIdValue = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID));
                @SuppressLint("Range")int studentIdValue = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID));
                @SuppressLint("Range")String sabaqPara = cursor.getString(cursor.getColumnIndex(COLUMN_SABAQ_PARA));
                @SuppressLint("Range")String sabaqSurah = cursor.getString(cursor.getColumnIndex(COLUMN_SABAQ_SURAH));
                @SuppressLint("Range")String sabaqVerse = cursor.getString(cursor.getColumnIndex(COLUMN_SABAQ_VERSE));
                @SuppressLint("Range")String sabaqi = cursor.getString(cursor.getColumnIndex(COLUMN_SABAQI_PARA));
                @SuppressLint("Range")String manzil = cursor.getString(cursor.getColumnIndex(COLUMN_MANZIL_PARA));
                @SuppressLint("Range")String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

                Task task = new Task(taskIdValue, studentIdValue,date, sabaqPara,sabaqSurah,sabaqVerse, sabaqi, manzil);

                tasks.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return tasks;
    }
    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SABAQ_STATUS, task.getSabaqStatus());
        values.put(COLUMN_MANZIL_STATUS, task.getManzilStatus());
        values.put(COLUMN_SABAQI_STATUS, task.getSabaqiStatus());

        // Specify the WHERE clause to update the specific task
        String whereClause = COLUMN_TASK_ID + " = ?";
        String[] whereArgs = { String.valueOf(task.getTaskId()) };

        // Update the row in the database
        int numRowsUpdated = db.update("tasks", values, whereClause, whereArgs);

        if (numRowsUpdated > 0) {
            Log.d("DatabaseHelper", "Task updated successfully");
        } else {
            Log.d("DatabaseHelper", "Failed to update task");
        }

        // Close the database connection
        db.close();
    }

    public long insertTask(Context context,Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get the sabaq para number and sabaqi para number from the task
        String sabaqPara = task.getSabaqPara();
        String sabaqiPara = task.getSabaqiPara();
        String manzilPara = task.getManzilPara();

        // Validate the conditions
        if (Integer.parseInt(manzilPara) >= Integer.parseInt(sabaqPara)) {
            Toast.makeText(context, "Manzil para number should be less than sabaq para number", Toast.LENGTH_SHORT).show();
            return -1;
        }

        if (Integer.parseInt(sabaqiPara) != Integer.parseInt(sabaqPara) - 1) {
            Toast.makeText(context, "Sabaqi para number should be one less than sabaq para number", Toast.LENGTH_SHORT).show();
            return -1;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, task.getStudentId());
        values.put(COLUMN_SABAQ_PARA, task.getSabaqPara());
        values.put(COLUMN_SABAQ_SURAH, task.getSabaqSurah());
        values.put(COLUMN_SABAQ_VERSE, task.getSabaqVerse());
        values.put(COLUMN_MANZIL_PARA, task.getManzilPara());
        values.put(COLUMN_SABAQI_PARA, task.getSabaqiPara());
        values.put(COLUMN_DATE,task.getDate());

        long taskId = db.insert("tasks", null, values);
        db.close();

        return taskId;
    }

    public List<Task> getTasksForStudent(int studentId) {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + COLUMN_STUDENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(studentId)};

        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();

                @SuppressLint("Range") int taskIdValue = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID));
                task.setTaskId(taskIdValue);

                @SuppressLint("Range") String dateValue = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                task.setDate(dateValue);

                @SuppressLint("Range") String sabaqParaValue = cursor.getString(cursor.getColumnIndex(COLUMN_SABAQ_PARA));
                task.setSabaqPara(sabaqParaValue);

                @SuppressLint("Range") String sabaqSurahValue = cursor.getString(cursor.getColumnIndex(COLUMN_SABAQ_SURAH));
                task.setSabaqSurah(sabaqSurahValue);

                @SuppressLint("Range") String sabaqVerseValue = cursor.getString(cursor.getColumnIndex(COLUMN_SABAQ_VERSE));
                task.setSabaqVerse(sabaqVerseValue);

                @SuppressLint("Range")
                String paraStatusValue = cursor.getString(cursor.getColumnIndex(COLUMN_SABAQ_STATUS));
                boolean paraStatus = Boolean.parseBoolean(paraStatusValue);
                task.setSabaqStatus(paraStatus);


                @SuppressLint("Range") String manzilParaValue = cursor.getString(cursor.getColumnIndex(COLUMN_MANZIL_PARA));
                task.setManzilPara(manzilParaValue);

                @SuppressLint("Range")
                String manzilStatusValue = cursor.getString(cursor.getColumnIndex(COLUMN_MANZIL_STATUS));
                boolean manzilStatus = Boolean.parseBoolean(manzilStatusValue);
                task.setManzilStatus(manzilStatus);

                @SuppressLint("Range") String sabaqiParaValue = cursor.getString(cursor.getColumnIndex(COLUMN_SABAQI_PARA));
                task.setSabaqiPara(sabaqiParaValue);

                @SuppressLint("Range")
                String sabaqiStatusValue = cursor.getString(cursor.getColumnIndex(COLUMN_SABAQI_STATUS));
                boolean sabaqiStatus = Boolean.parseBoolean(sabaqiStatusValue);
                task.setSabaqiStatus(sabaqiStatus);

                taskList.add(task);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return taskList;
    }




}
