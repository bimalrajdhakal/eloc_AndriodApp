package com.nexogen.routefinder.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nexogen.routefinder.intefaces.BookMarkDao;
import com.nexogen.routefinder.intefaces.NavigatorDao;


@Database(entities = {BookMarkTable.class, NavigatorTable.class},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "locationdatbase")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract BookMarkDao bookMarkDao();

    public abstract NavigatorDao navigatorDao();


}