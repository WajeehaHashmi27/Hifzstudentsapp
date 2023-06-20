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

        // Retrieve the studentId and studentName from the intent
        studentId = getIntent().getIntExtra("studentId", -1);
        studentName = getIntent().getStringExtra("studentName");
        studentClass = getIntent().getStringExtra("studentClass");
        studentAge = getIntent().getIntExtra("studentAge", -1);
        // Set the title of the activity as the student's name
        setTitle(studentName);
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

        // Retrieve and display the student details
        Student student = databaseHelper.getStudentById(studentId);
        if (student != null) {
            textViewStudentId.setText("Roll number: " +String.valueOf(student.getStudentId()));
            textViewStudentName.setText("Name: " +student.getName());
            textViewStudentAge.setText("Age: "+String.valueOf(student.getAge()));
            textViewStudentClass.setText("Class: "+student.getClassName());
        }
        buttonUpdateAssignTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckAssignTaskActivity.this, AssignTask.class);
                intent.putExtra("studentId", studentId);
                intent.putExtra("studentName", studentName);
                intent.putExtra("studentClass", studentClass);
                intent.putExtra("studentAge", studentAge);
                startActivity(intent);

            }
        });
    }
}
