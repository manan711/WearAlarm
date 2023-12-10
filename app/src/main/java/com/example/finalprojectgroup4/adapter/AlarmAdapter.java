package com.example.finalprojectgroup4.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectgroup4.databinding.AlarmRowBinding;
import com.example.finalprojectgroup4.model.Alarm;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    AlarmRowBinding rowBinding;
    private List<Alarm> myAlarms;
    Context context;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
