package com.example.hifzstudentsapp;



import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;
import android.widget.RadioButton;
import java.util.List;

public class CheckAssignTaskActivity extends AppCompatActivity {

    private TextView textViewStudentId, textViewStudentName, textViewStudentAge, textViewStudentClass;
    private TextView textViewSabaqHeading, textViewSabaqTask;
    private TextView textViewManzilHeading, textViewManzilTask;
    private TextView textViewSabaqiHeading, textViewSabaqiTask;
    private TextView textViewDate;
    private RadioButton radioButtonSabaqYes, radioButtonSabaqNo;
    private RadioButton radioButtonManzilYes, radioButtonManzilNo;
    private RadioButton radioButtonSabaqiYes, radioButtonSabaqiNo;
    private Button buttonUpdateAssignTask;

    private int studentId;
    private String studentName;
    private DatabaseHelper databaseHelper;
    private String studentClass;
    private int studentAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_assign_task);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        // Retrieve the studentId and studentName from the intent
        studentId = getIntent().getIntExtra("studentId", -1);
        studentName = getIntent().getStringExtra("studentName");
        studentClass = getIntent().getStringExtra("studentClass");
        studentAge = getIntent().getIntExtra("studentAge", -1);
        // Set the title of the activity as the student's name
        //setTitle(studentName);
        databaseHelper = new DatabaseHelper(this);
        // Initialize views
        textViewStudentId = findViewById(R.id.textViewStudentId);
        textViewStudentName = findViewById(R.id.textViewStudentName);
        textViewStudentAge = findViewById(R.id.textViewStudentAge);
        textViewStudentClass = findViewById(R.id.textViewStudentClass);
        textViewDate = findViewById(R.id.textViewDate);
        textViewSabaqHeading = findViewById(R.id.textViewSabaqHeading);
        textViewSabaqTask = findViewById(R.id.textViewSabaqTask);
        textViewManzilHeading = findViewById(R.id.textViewManzilHeading);
        textViewManzilTask = findViewById(R.id.textViewManzilTask);
        textViewSabaqiHeading = findViewById(R.id.textViewSabaqiHeading);
        textViewSabaqiTask = findViewById(R.id.textViewSabaqiTask);
        radioButtonSabaqYes = findViewById(R.id.radioButtonSabaqYes);
        radioButtonSabaqNo = findViewById(R.id.radioButtonSabaqNo);
        radioButtonManzilYes = findViewById(R.id.radioButtonManzilYes);
        radioButtonManzilNo = findViewById(R.id.radioButtonManzilNo);
        radioButtonSabaqiYes = findViewById(R.id.radioButtonSabaqiYes);
        radioButtonSabaqiNo = findViewById(R.id.radioButtonSabaqiNo);
        buttonUpdateAssignTask = findViewById(R.id.buttonUpdateAssignTask);
        List<Task> tasks = databaseHelper.getTasksByStudentIdAndTaskId(studentId);
        // Retrieve and display the student details
        Student student = databaseHelper.getStudentById(studentId);
        if (student != null) {
            textViewStudentId.setText("Roll number: " +String.valueOf(student.getStudentId()));
            textViewStudentName.setText("Name: " +student.getName());
            textViewStudentAge.setText("Age: "+String.valueOf(student.getAge()));
            textViewStudentClass.setText("Class: "+student.getClassName());
        }

        if (tasks.isEmpty()) {
            // If no tasks found, set the text to "No assigned task yet"
            textViewSabaqTask.setText("No assigned task yet");
            textViewManzilTask.setText("No assigned task yet");
            textViewSabaqiTask.setText("No assigned task yet");

            radioButtonSabaqYes.setEnabled(false);
            radioButtonSabaqNo.setEnabled(false);
            radioButtonManzilYes.setEnabled(false);
            radioButtonManzilNo.setEnabled(false);
            radioButtonSabaqiYes.setEnabled(false);
            radioButtonSabaqiNo.setEnabled(false);
            buttonUpdateAssignTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CheckAssignTaskActivity.this, AssignTask.class);
                    intent.putExtra("studentId", studentId);
                    intent.putExtra("studentName", studentName);
                    intent.putExtra("studentClass", studentClass);
                    intent.putExtra("studentAge", studentAge);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            // Get the last task from the list (assuming tasks are ordered by entry date)
            Task lastTask = tasks.get(tasks.size() - 1);

            // Set the corresponding task values to the TextView elements
            textViewDate.setText("Date: "+lastTask.getDate());
            textViewSabaqTask.setText("Para: "+lastTask.getSabaqPara()+", Surah: "+lastTask.getSabaqSurah()+", Verse: "+lastTask.getSabaqVerse());
            textViewManzilTask.setText("Para: "+lastTask.getManzilPara());
            textViewSabaqiTask.setText("Para: "+lastTask.getSabaqiPara());

            buttonUpdateAssignTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the selected radio button values
                    boolean sabaqStatus = radioButtonSabaqYes.isChecked();
                    boolean manzilStatus = radioButtonManzilYes.isChecked();
                    boolean sabaqiStatus = radioButtonSabaqiYes.isChecked();

              // Check if "No" options are selected and update the status accordingly
                    if (radioButtonSabaqNo.isChecked()) {
                        sabaqStatus = false;
                    }
                    if (radioButtonManzilNo.isChecked()) {
                        manzilStatus = false;
                    }
                    if (radioButtonSabaqiNo.isChecked()) {
                        sabaqiStatus = false;
                    }

                    lastTask.setSabaqStatus(sabaqStatus);
                    lastTask.setManzilStatus(manzilStatus);
                    lastTask.setSabaqiStatus(sabaqiStatus);
                    databaseHelper.updateTask(lastTask);
                    Intent intent = new Intent(CheckAssignTaskActivity.this, AssignTask.class);
                    intent.putExtra("studentId", studentId);
                    intent.putExtra("studentName", studentName);
                    intent.putExtra("studentClass", studentClass);
                    intent.putExtra("studentAge", studentAge);
                    intent.putExtra("sabaqPara", lastTask.getSabaqPara());
                    intent.putExtra("sabaqSurah", lastTask.getSabaqSurah());
                    intent.putExtra("sabaqVerse", lastTask.getSabaqVerse());
                    intent.putExtra("sabaqStatus", sabaqStatus);
                    intent.putExtra("manzilPara", lastTask.getManzilPara());
                    intent.putExtra("manzilStatus", manzilStatus);
                    intent.putExtra("sabaqiPara", lastTask.getSabaqiPara());
                    intent.putExtra("sabaqiStatus", sabaqiStatus);

                    startActivity(intent);
                    finish();
                }
            });
        }

    }
}
