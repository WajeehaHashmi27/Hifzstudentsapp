package com.example.hifzstudentsapp;

import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.widget.Toast;
import android.app.AlertDialog;

import java.util.List;

public class ListOfStudentsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewStudents;
    private StudentAdapter studentAdapter;
    private DatabaseHelper databaseHelper;
    private List<Student> studentList;
    private static final int UPDATE_STUDENT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_students);

        recyclerViewStudents = findViewById(R.id.recyclerViewStudents);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        // Retrieve the list of students from the database
        studentList = databaseHelper.getAllStudents();

        // Set up the RecyclerView adapter
        studentAdapter = new StudentAdapter(studentList);
        recyclerViewStudents.setAdapter(studentAdapter);
    }

    // Update a student record
    // Delete a student record
    public void deleteStudent(int studentId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListOfStudentsActivity.this);
        builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to delete this student record?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDeleted = databaseHelper.deleteStudent(studentId);
                        if (isDeleted) {
                            Toast.makeText(ListOfStudentsActivity.this, "Record deleted successfully", Toast.LENGTH_SHORT).show();
                            // Refresh the student list in the RecyclerView
                            studentList = databaseHelper.getAllStudents();
                            studentAdapter.updateStudents(studentList); // Update the adapter with the new student list
                        } else {
                            Toast.makeText(ListOfStudentsActivity.this, "Failed to delete record", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Update a student record
    public void updateStudent(int studentId) {
        Intent intent = new Intent(ListOfStudentsActivity.this, UpdateStudentActivity.class);
        intent.putExtra("studentId", studentId);
        startActivityForResult(intent, UPDATE_STUDENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_STUDENT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Refresh the student list in the RecyclerView
            studentList = databaseHelper.getAllStudents();
            studentAdapter.updateStudents(studentList); // Update the adapter with the new student list
        }
    }

}
