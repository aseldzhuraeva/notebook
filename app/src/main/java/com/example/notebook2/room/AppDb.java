package com.example.notebook2.room;
import  androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.notebook2.models.Student;

@Database(entities = {Student.class},version = 1)
public abstract class AppDb extends RoomDatabase{
    public abstract StudentDao studentDao();

}
