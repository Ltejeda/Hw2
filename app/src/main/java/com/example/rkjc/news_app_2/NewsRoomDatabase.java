package com.example.rkjc.news_app_2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;
import android.content.Context;

import java.util.WeakHashMap;

@Database(entities = {NewsItem.class}, version = 1)
public abstract class NewsRoomDatabase extends RoomDatabase{

    public abstract NewsItemDao newsItemDao();

    private static NewsRoomDatabase INSTANCE;

    public static NewsRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (NewsRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsRoomDatabase.class, "news_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
