package com.example.swimtrack;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TimeRepository {
    private TimeDao timeDao;
    private LiveData<List<Time>> allTimes;

    public TimeRepository(Application application) {
        TimeDatabase database = TimeDatabase.getInstance(application);
        timeDao = database.timeDao();
        allTimes = timeDao.getAllTimes();
    }

    //ViewModel calls these three methods to modify data
    public void insert(Time time) {
        new InsertTimeAsyncTask(timeDao).execute(time);
    }

    public void update(Time time) {
        new UpdateTimeAsyncTask(timeDao).execute(time);
    }

    public void delete(Time time) {
        new DeleteTimeAsyncTask(timeDao).execute(time);
    }

    public void deleteAllTimes() {
        new DeleteAllTimesAsyncTask(timeDao).execute();
    }

    public LiveData<List<Time>> getAllTimes() {
        return allTimes;
    }

    //AsyncTasks allow change data in background without crashing app
    private static class InsertTimeAsyncTask extends AsyncTask<Time, Void, Void> {
        private TimeDao timeDao;

        private InsertTimeAsyncTask(TimeDao timeDao) {
            this.timeDao = timeDao;
        }

        @Override
        protected Void doInBackground(Time... times) {
            timeDao.insert(times[0]);
            return null;
        }
    }

    private static class UpdateTimeAsyncTask extends AsyncTask<Time, Void, Void> {
        private TimeDao timeDao;

        private UpdateTimeAsyncTask(TimeDao timeDao) {
            this.timeDao = timeDao;
        }

        @Override
        protected Void doInBackground(Time... times) {
            timeDao.update(times[0]);
            return null;
        }
    }

    private static class DeleteTimeAsyncTask extends AsyncTask<Time, Void, Void> {
        private TimeDao timeDao;

        private DeleteTimeAsyncTask(TimeDao timeDao) {
            this.timeDao = timeDao;
        }

        @Override
        protected Void doInBackground(Time... times) {
            timeDao.delete(times[0]);
            return null;
        }
    }

    private static class DeleteAllTimesAsyncTask extends AsyncTask<Void, Void, Void> {
        private TimeDao timeDao;

        private DeleteAllTimesAsyncTask(TimeDao timeDao) {
            this.timeDao = timeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            timeDao.deleteAllTimes();
            return null;
        }
    }
}
