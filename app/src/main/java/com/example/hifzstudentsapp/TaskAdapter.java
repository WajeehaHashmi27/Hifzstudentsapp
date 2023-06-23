package com.example.hifzstudentsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.content.Context;
import java.util.List;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;



public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        // Set the values for the views in the item_task layout
        holder.textViewDate.setText("Date: "+task.getDate());
        holder.textViewSabaqPara.setText("Sabaq: "+task.getSabaqPara()+"(Para), "+task.getSabaqSurah()+"(Surah), "+task.getSabaqVerse()+"(Verse)");
        holder.textViewSabaqSurah.setText("Manzil: "+task.getManzilPara()+"(Para)");
        holder.textViewSabaqVerse.setText("Sabaqi: "+task.getSabaqiPara()+"(Para)");
        // Set other views here

        // Add any click listeners or other functionality here
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewSabaqPara, textViewSabaqSurah, textViewSabaqVerse;
        // Add other views here

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewSabaqPara = itemView.findViewById(R.id.textViewSabaqPara);
            textViewSabaqSurah = itemView.findViewById(R.id.textViewSabaqSurah);
            textViewSabaqVerse = itemView.findViewById(R.id.textViewSabaqVerse);
            // Initialize other views here
        }
    }
}

