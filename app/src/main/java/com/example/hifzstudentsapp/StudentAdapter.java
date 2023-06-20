package com.example.hifzstudentsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.content.Context;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> studentList;
    private Context context;

    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // ...
        Student student = studentList.get(position);
        holder.bind(student);
        // Set an OnClickListener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = studentList.get(position);

                // Start the StudentDetailsActivity and pass the student's id and name as extras
                Intent intent = new Intent(context, StudentDetailsActivity.class);
                intent.putExtra("studentId", student.getStudentId());
                intent.putExtra("studentName", student.getName());
                intent.putExtra("studentClass", student.getClassName());
                intent.putExtra("studentAge", student.getAge());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return studentList.size();
    }
    public void updateStudents(List<Student> updatedStudents) {
        studentList = updatedStudents;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewId, textViewName;
        private Button buttonUpdate, buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewName = itemView.findViewById(R.id.textViewName);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }

        public void bind(Student student) {
            textViewId.setText(String.valueOf(student.getStudentId())); // Convert the ID to a string
            textViewName.setText(student.getName());

            // Set click listeners for the update and delete buttons
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call the updateStudent method from the activity and pass the student ID
                    int studentId = student.getStudentId();
                    ((ListOfStudentsActivity) itemView.getContext()).updateStudent(studentId);
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call the deleteStudent method from the activity and pass the student ID
                    int studentId = student.getStudentId();
                    ((ListOfStudentsActivity) itemView.getContext()).deleteStudent(studentId);
                }
            });
        }
    }
}
