package com.example.roomdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {

    abstract UserDao userDao();

    static UserRoomDatabase instance;

    static UserRoomDatabase getDatabase(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,UserRoomDatabase.class,"user_database").allowMainThreadQueries().build();
        }
        return instance;
    }

}
