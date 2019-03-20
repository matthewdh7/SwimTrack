package com.example.swimtrack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hungmat20 on 3/6/2019.
 */

public class BestTimesFragment extends Fragment {
    public static final int ADD_TIME_REQUEST = 1;
    private TimeViewModel timeViewModel;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_besttimes, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTimesB);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final TimeAdapter adapter = new TimeAdapter();
        recyclerView.setAdapter(adapter);

        timeViewModel = ViewModelProviders.of(getActivity()).get(TimeViewModel.class);
        timeViewModel.getBestTimes().observe(this, new Observer<List<Time>>() {
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

        return view;
    }

}
