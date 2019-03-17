package com.example.swimtrack;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Time.class}, version = 1)
public abstract class TimeDatabase extends RoomDatabase {

    private static TimeDatabase instance;

    public abstract TimeDao timeDao();

    public static synchronized TimeDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TimeDatabase.class, "time_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TimeDao timeDao;

        private PopulateDbAsyncTask(TimeDatabase db) {
            timeDao = db.timeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            timeDao.insert(new Time("Example Event (swipe to remove)", "0:00.00", "1/1/19", false));
            return null;
        }
    }
}
