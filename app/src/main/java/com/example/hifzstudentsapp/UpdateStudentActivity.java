package com.example.hifzstudentsapp;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class UpdateStudentActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextClass;
    private Button buttonUpdate;

    private DatabaseHelper databaseHelper;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_student);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextClass = findViewById(R.id.editTextClass);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        databaseHelper = new DatabaseHelper(this);

        // Retrieve the studentId from the intent
        studentId = getIntent().getIntExtra("studentId", -1);

        // Retrieve the current student record from the database and populate the UI fields
        Student student = databaseHelper.getStudentById(studentId);
        if (student != null) {
            editTextName.setText(student.getName());
            editTextAge.setText(String.valueOf(student.getAge()));
            editTextClass.setText(student.getClassName());
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the updated values from the UI fields
                String name = editTextName.getText().toString().trim();
                int age = Integer.parseInt(editTextAge.getText().toString().trim());
                String className = editTextClass.getText().toString().trim();

                // Update the student record in the database
                boolean isUpdated = databaseHelper.updateStudent(studentId, name, age, className);

                if (isUpdated) {
                    Toast.makeText(UpdateStudentActivity.this, "Record updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateStudentActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UpdateStudentActivity.this, "Failed to update record", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

