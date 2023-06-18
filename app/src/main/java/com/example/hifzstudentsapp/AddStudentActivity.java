package com.example.hifzstudentsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class AddStudentActivity extends AppCompatActivity {

    private EditText editTextId, editTextName, editTextAge, editTextClass;
    private Button buttonAdd;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        // Initialize views

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextClass = findViewById(R.id.editTextClass);
        buttonAdd = findViewById(R.id.buttonAdd);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve entered student details

                String name = editTextName.getText().toString().trim();
                String ageString = editTextAge.getText().toString().trim();
                int age;
                String studentClass = editTextClass.getText().toString().trim();

                // Validate the inputs
                if ( name.isEmpty() || ageString.isEmpty() || studentClass.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Parse age
                try {
                    age = Integer.parseInt(ageString);
                } catch (NumberFormatException e) {
                    Toast.makeText(AddStudentActivity.this, "Please enter a valid age", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save student to the database
                boolean success = databaseHelper.addStudent( name, age, studentClass);
                if (success) {
                    Toast.makeText(AddStudentActivity.this, "Student added successfully", Toast.LENGTH_SHORT).show();

                    finish(); // Finish the activity and go back to the previous screen
                } else {
                    Toast.makeText(AddStudentActivity.this, "Failed to add student", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

