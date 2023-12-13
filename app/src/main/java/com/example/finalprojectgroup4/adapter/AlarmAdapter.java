package com.example.finalprojectgroup4.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalprojectgroup4.databinding.AlarmRowBinding;
import com.example.finalprojectgroup4.model.Alarm;
import com.example.finalprojectgroup4.activity.EditActivity; // Make sure to import your EditActivity
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private final List<Alarm> myAlarms;
    private Context context;

    public AlarmAdapter(List<Alarm> myAlarms, Context context) {
        this.myAlarms = myAlarms;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AlarmRowBinding rowBinding = AlarmRowBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(rowBinding);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AlarmRowBinding recyclerRowBinding;

        public ViewHolder(AlarmRowBinding rowBinding) {
            super(rowBinding.getRoot());
            this.recyclerRowBinding = rowBinding;
        }

        void bindView(final Alarm alarm, final Context context) {
            recyclerRowBinding.alarmToggle.setText(alarm.getAlarmText());

            recyclerRowBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start EditActivity with the alarm ID or other relevant data
                    Intent intent = new Intent(context, EditActivity.class);
                    intent.putExtra("ALARM_ID", alarm.getId()); // pass the alarm ID
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alarm alarm = myAlarms.get(position);
        holder.bindView(alarm, context);
    }

    @Override
    public int getItemCount() {
        return myAlarms.size();
    }
}
