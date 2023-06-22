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

public class AssignTask extends AppCompatActivity {

    private TextView textViewStudentId, textViewStudentName, textViewStudentAge, textViewStudentClass;

    private int studentId;
    private String studentName;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);

        // Retrieve the studentId and studentName from the intent
        studentId = getIntent().getIntExtra("studentId", -1);
        studentName = getIntent().getStringExtra("studentName");
        String studentClass = getIntent().getStringExtra("studentClass");
        int studentAge = getIntent().getIntExtra("studentAge", -1);
        String sabaqPara = getIntent().getStringExtra("sabaqPara");
        String sabaqSurah = getIntent().getStringExtra("sabaqSurah");
        String sabaqVerse = getIntent().getStringExtra("sabaqVerse");
        boolean sabaqStatus = getIntent().getBooleanExtra("sabaqStatus", false);
        String manzilPara = getIntent().getStringExtra("manzilPara");
        boolean manzilStatus = getIntent().getBooleanExtra("manzilStatus", false);
        String sabaqiPara = getIntent().getStringExtra("sabaqiPara");
        boolean sabaqiStatus = getIntent().getBooleanExtra("sabaqiStatus", false);
        // Set the title of the activity as the student's name
        //setTitle(studentName);
        databaseHelper = new DatabaseHelper(this);
        // Initialize views
        textViewStudentId = findViewById(R.id.textViewStudentId);
        textViewStudentName = findViewById(R.id.textViewStudentName);
        textViewStudentAge = findViewById(R.id.textViewStudentAge);
        textViewStudentClass = findViewById(R.id.textViewStudentClass);
        TextView textViewSabaqStatus1 = findViewById(R.id.textViewSabaqStatus1);
        TextView textViewManzilStatus1 = findViewById(R.id.textViewManzilStatus1);
        TextView textViewSabaqiStatus1 = findViewById(R.id.textViewSabaqiStatus1);


        // Retrieve and display the student details
        Student student = databaseHelper.getStudentById(studentId);
        if (student != null) {
            textViewStudentId.setText("Roll number: " + String.valueOf(student.getStudentId()));
            textViewStudentName.setText("Name: " + student.getName());
            textViewStudentAge.setText("Age: " + String.valueOf(student.getAge()));
            textViewStudentClass.setText("Class: " + student.getClassName());
        }
        // Update the status text based on the sabaqStatus, manzilStatus, and sabaqiStatus
        if (sabaqStatus) {
            String sabaqStatusText = "Previous Sabaq done which was Para: " + sabaqPara +
                    ", Surah: " + sabaqSurah + ", Verse: " + sabaqVerse;
            textViewSabaqStatus1.setText(sabaqStatusText);
        } else {
            String sabaqStatusText = "Previous Sabaq not done which was Para: " + sabaqPara +
                    ", Surah: " + sabaqSurah + ", Verse: " + sabaqVerse;
            textViewSabaqStatus1.setText(sabaqStatusText);
        }

        if (manzilStatus) {
            String manzilStatusText = "Previous Manzil done which was Para: " + manzilPara;
            textViewManzilStatus1.setText(manzilStatusText);
        } else {
            String manzilStatusText = "Previous Manzil not done which was Para: " + manzilPara;
            textViewManzilStatus1.setText(manzilStatusText);
        }

        if (sabaqiStatus) {
            String sabaqiStatusText = "Previous Sabaqi done which was Para: " + sabaqiPara;
            textViewSabaqiStatus1.setText(sabaqiStatusText);
        } else {
            String sabaqiStatusText = "Previous Sabaqi not done which was Para: " + sabaqiPara;
            textViewSabaqiStatus1.setText(sabaqiStatusText);
        }


        Button buttonAssignTask = findViewById(R.id.buttonAssignTask);
        buttonAssignTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the values entered in the EditText fields
                EditText editTextDate = findViewById(R.id.editTextDate);
                EditText editTextSabaqPara = findViewById(R.id.editTextSabaqPara);
                EditText editTextSabaqSurah = findViewById(R.id.editTextSabaqSurah);
                EditText editTextSabaqVerse = findViewById(R.id.editTextSabaqVerse);
                EditText editTextManzilPara = findViewById(R.id.editTextManzilPara);
                EditText editTextSabaqiPara = findViewById(R.id.editTextSabaqiPara);

                String sabaqParaNumber = editTextSabaqPara.getText().toString();
                String sabaqSurah = editTextSabaqSurah.getText().toString();
                String sabaqVerse = editTextSabaqVerse.getText().toString();
                String manzilParaNumber = editTextManzilPara.getText().toString();
                String sabaqiParaNumber = editTextSabaqiPara.getText().toString();
                String date = editTextDate.getText().toString();

                // Update the task values in the database
                Task task = new Task();
                task.setStudentId(studentId);
                task.setSabaqPara(sabaqParaNumber);
                task.setSabaqSurah(sabaqSurah);
                task.setSabaqVerse(sabaqVerse);
                task.setManzilPara(manzilParaNumber);
                task.setSabaqiPara(sabaqiParaNumber);
                task.setDate(date);

                long taskId = databaseHelper.insertTask(AssignTask.this,task);
                if (taskId != -1) {
                    Toast.makeText(AssignTask.this, "Task assigned successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AssignTask.this, "Failed to assign task.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}
