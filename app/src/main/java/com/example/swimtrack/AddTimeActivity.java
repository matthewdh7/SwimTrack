package com.example.swimtrack;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class AddTimeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_NAME =
            "com.example.swimtrack.EXTRA_NAME";
    public static final String EXTRA_TIME =
            "com.example.swimtrack.EXTRA_TIME";
    public static final String EXTRA_DATE =
            "com.example.swimtrack.EXTRA_DATE";
    public static final String EXTRA_BESTTIME =
            "com.example.swimtrack.EXTRA_BESTTIME";

    private EditText editTextName;
    private EditText editTextTime;
    private EditText editTextDate;
    private CheckBox buttonBestTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        editTextName = findViewById(R.id.edit_text_name);
        editTextTime = findViewById(R.id.edit_text_time);
        editTextDate = findViewById(R.id.edit_text_date);
        buttonBestTime = (CheckBox) findViewById(R.id.checkBox);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Add Time");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String formattedMonth = (month + 1) + "-";
        String formattedDay = dayOfMonth + "-";
        String formattedYear = String.valueOf(year);

        editTextDate.setText(formattedMonth + formattedDay + formattedYear);
    }

    private void saveTime() {
        String name = editTextName.getText().toString();
        String time = editTextTime.getText().toString();
        String date = editTextDate.getText().toString();
        boolean bestTime;

        if (name.trim().isEmpty() || time.trim().isEmpty()) {
            Toast.makeText(this, "Enter an event, time, and date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (date.trim().isEmpty()) {
            Toast.makeText(this, "Using today's date", Toast.LENGTH_SHORT).show();
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-YYYY");
            String formattedToday = dateFormat.format(today);
            date = formattedToday;
        }

        if (buttonBestTime.isChecked()) {
            bestTime = true;
        } else {
            bestTime = false;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_TIME, time);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_BESTTIME, bestTime);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_time_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_time:
                saveTime();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
