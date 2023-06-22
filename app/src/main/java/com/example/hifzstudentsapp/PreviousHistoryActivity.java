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
import java.util.List;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

public class PreviousHistoryActivity extends AppCompatActivity {

    private TextView textViewStudentId, textViewStudentName, textViewStudentAge, textViewStudentClass;

    private int studentId;
    private String studentName;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_history);

        // Retrieve the studentId and studentName from the intent
        studentId = getIntent().getIntExtra("studentId", -1);
        studentName = getIntent().getStringExtra("studentName");

        // Set the title of the activity as the student's name
        //setTitle(studentName);
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

        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);

        // Retrieve the tasks for the student from the database
        taskList.addAll(databaseHelper.getTasksForStudent(studentId));
        taskAdapter.notifyDataSetChanged();

    }
}
