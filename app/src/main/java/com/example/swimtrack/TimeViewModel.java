package com.example.swimtrack;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TimeViewModel extends AndroidViewModel {
    private TimeRepository repository;
    private LiveData<List<Time>> allTimes;
    private MutableLiveData<CharSequence> text = new MutableLiveData<>();

    public TimeViewModel(@NonNull Application application) {
        super(application);
        repository = new TimeRepository(application);
        allTimes = repository.getAllTimes();
    }

    public void setText(CharSequence input) {
        text.setValue(input);
    }

    public LiveData<CharSequence> getText() {
        return text;
    }

    public void insert(Time time) {
        repository.insert(time);
    }

    public void update(Time time) {
        repository.update(time);
    }

    public void delete(Time time) {
        repository.delete(time);
    }

    public void deleteAllTimes() {
        repository.deleteAllTimes();
    }

    public LiveData<List<Time>> getAllTimes() {
        return allTimes;
    }
}
