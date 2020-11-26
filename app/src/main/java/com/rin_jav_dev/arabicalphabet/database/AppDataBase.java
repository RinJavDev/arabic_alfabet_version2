package com.rin_jav_dev.arabicalphabet.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.rin_jav_dev.arabicalphabet.database.alifs.AlifsModelDao;
import com.rin_jav_dev.arabicalphabet.database.alifs.Alif;

@Database(entities = {Alif.class
}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract AlifsModelDao alifsModelDao();
}
