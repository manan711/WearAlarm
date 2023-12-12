package com.example.finalprojectgroup4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectgroup4.databinding.AlarmRowBinding;
import com.example.finalprojectgroup4.model.Alarm;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    AlarmRowBinding rowBinding;
    private final List<Alarm> myAlarms;

    public AlarmAdapter(List<Alarm> myAlarms, Context context) {
        super();
        this.myAlarms = myAlarms;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        rowBinding=AlarmRowBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(rowBinding);
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        AlarmRowBinding recyclerRowBinding;
        public ViewHolder(AlarmRowBinding rowBinding) {
            super(rowBinding.getRoot());
            this.recyclerRowBinding = rowBinding;
        }
        void bindView(final String alarmText, final int position) {
            recyclerRowBinding.alarmToggle.setText(alarmText);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(myAlarms.get(position).getAlarmText(),position);
    }

    @Override
    public int getItemCount() {
        return myAlarms.size();
    }
}
