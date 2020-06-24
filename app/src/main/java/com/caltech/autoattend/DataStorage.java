package com.caltech.autoattend;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1, entities = {User.class, Subject.class, ClassSession.class, SessionSchedule.class, SemesterDate.class}, exportSchema = false)
public abstract class DataStorage extends RoomDatabase {

    public abstract DAOs daos();

    private static volatile DataStorage INSTANCE;
    private static final int NUMBER_OF_THREADS = 5;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DataStorage getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataStorage.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            DataStorage.class, "database-name").build();
                }
            }
        }
        return INSTANCE;
    }
}
