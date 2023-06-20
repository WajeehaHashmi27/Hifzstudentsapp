package com.example.hifzstudentsapp;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;
import android.widget.RadioButton;

public class PreviousHistoryActivity extends AppCompatActivity {

    private TextView textViewStudentId, textViewStudentName, textViewStudentAge, textViewStudentClass;

    private int studentId;
    private String studentName;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_history);

        // Retrieve the studentId and studentName from the intent
        studentId = getIntent().getIntExtra("studentId", -1);
        studentName = getIntent().getStringExtra("studentName");

        // Set the title of the activity as the student's name
        setTitle(studentName);
        databaseHelper = new DatabaseHelper(this);
        // Initialize views
        textViewStudentId = findViewById(R.id.textViewStudentId);
        textViewStudentName = findViewById(R.id.textViewStudentName);
        textViewStudentAge = findViewById(R.id.textViewStudentAge);
        textViewStudentClass = findViewById(R.id.textViewStudentClass);


        // Retrieve and display the student details
        Student student = databaseHelper.getStudentById(studentId);
        if (student != null) {
            textViewStudentId.setText("Roll number: " +String.valueOf(student.getStudentId()));
            textViewStudentName.setText("Name: " +student.getName());
            textViewStudentAge.setText("Age: "+String.valueOf(student.getAge()));
            textViewStudentClass.setText("Class: "+student.getClassName());
        }

    }
}
