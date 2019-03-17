package com.example.swimtrack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hungmat20 on 3/6/2019.
 */

public class BestTimesFragment extends Fragment {
    private TimeViewModel timeViewModel;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

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

        return view;
    }
}
