package com.example.swimtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by hungmat20 on 3/6/2019.
 */

public class TimesFragment extends Fragment {
    public static final int ADD_TIME_REQUEST = 1;
    private TimeViewModel timeViewModel;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_times, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTimesA);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final TimeAdapter adapter = new TimeAdapter();
        recyclerView.setAdapter(adapter);

        timeViewModel = ViewModelProviders.of(getActivity()).get(TimeViewModel.class);
        timeViewModel.getAllTimes().observe(this, new Observer<List<Time>>() {
            @Override
            public void onChanged(@Nullable List<Time> times) {
                adapter.setTimes(times);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                timeViewModel.delete(adapter.getTimeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Entry removed", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        FloatingActionButton buttonAddTime = view.findViewById(R.id.button_add_time);
        buttonAddTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTimeActivity.class);
                startActivityForResult(intent, ADD_TIME_REQUEST);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TIME_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddTimeActivity.EXTRA_NAME);
            String time = data.getStringExtra(AddTimeActivity.EXTRA_TIME);
            String date = data.getStringExtra(AddTimeActivity.EXTRA_DATE);
            boolean bestTime = data.getBooleanExtra(AddTimeActivity.EXTRA_BESTTIME, false);

            Time newTime = new Time(name, time, date, bestTime);
            timeViewModel = ViewModelProviders.of(this).get(TimeViewModel.class);
            timeViewModel.insert(newTime);

            Toast.makeText(getContext(), "New time entered", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "New time not entered", Toast.LENGTH_SHORT).show();
        }
    }
}
