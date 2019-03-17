package com.example.swimtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static android.app.Activity.RESULT_OK;

/**
 * Created by hungmat20 on 3/6/2019.
 */

public class HomeFragment extends Fragment {
    public static final int ADD_TIME_REQUEST = 1;
    private TimeViewModel timeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton buttonAddTime = v.findViewById(R.id.button_add_time);
        buttonAddTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTimeActivity.class);
                startActivityForResult(intent, ADD_TIME_REQUEST);
            }
        });

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TIME_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddTimeActivity.EXTRA_NAME);
            String time = data.getStringExtra(AddTimeActivity.EXTRA_TIME);
            String date = data.getStringExtra(AddTimeActivity.EXTRA_DATE);

            Time newTime = new Time(name, time, date);
            timeViewModel = ViewModelProviders.of(this).get(TimeViewModel.class);
            timeViewModel.insert(newTime);

            Toast toast = Toast.makeText(getContext(), "New time entered", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0 ,0);
            toast.show();

        } else {
            Toast.makeText(getContext(), "New time not entered", Toast.LENGTH_SHORT).show();
        }
    }
}
