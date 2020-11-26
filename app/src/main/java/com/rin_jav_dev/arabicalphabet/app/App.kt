package com.rin_jav_dev.arabicalphabet.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.gms.ads.MobileAds
import com.rin_jav_dev.arabicalphabet.database.AppDataBase

class App: Application() {

companion object{
    private lateinit var sInstance: App
    lateinit var db: AppDataBase

    open fun getAppInstance(): App {
        return sInstance
    }
}


    override fun onCreate() {
        super.onCreate()
        sInstance = this
         db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "database-name"
        ).build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(this);
    }

}