package com.tavleen.mynotesapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDB extends RoomDatabase {
     public abstract NoteDao getNoteDao();
     private static NoteDB noteDB;
     public static NoteDB getInstance(Context context){
         if(null==noteDB){
             noteDB = buildDatabaseInstance(context);
         }
         return noteDB;
     }

    private static NoteDB buildDatabaseInstance(Context context) {
         return Room.databaseBuilder(context,NoteDB.class,"note")
                 .allowMainThreadQueries().build();
    }
}
