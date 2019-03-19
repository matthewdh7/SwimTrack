package com.example.swimtrack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeHolder> {
    private List<Time> times = new ArrayList<>();

    @NonNull
    @Override
    public TimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_item, parent, false);
        return new TimeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeHolder holder, int position) {
        Time currentTime  = times.get(position);
        holder.textViewEvent.setText(currentTime.getName());
        holder.textViewDate.setText(currentTime.getDate());
        holder.textViewNewTime.setText(currentTime.getNewTime());
        if(currentTime.isBestTime() == true) {
            holder.textViewNewTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_star, 0);
        } else {
            holder.textViewNewTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public void setTimes(List<Time> times) {
        this.times = times;
        notifyDataSetChanged();
    }

    public Time getTimeAt(int position) {
        return times.get(position);
    }

    class TimeHolder extends RecyclerView.ViewHolder {
        private TextView textViewEvent;
        private TextView textViewNewTime;
        private TextView textViewDate;

        public TimeHolder(View itemView) {
            super(itemView);
            textViewEvent = itemView.findViewById(R.id.text_view_event);
            textViewNewTime = itemView.findViewById(R.id.text_view_newTime);
            textViewDate = itemView.findViewById(R.id.text_view_date);
        }
    }
}