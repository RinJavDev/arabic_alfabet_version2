package com.rin_jav_dev.arabicalphabet.app

import android.content.Context
import android.content.SharedPreferences
import com.rin_jav_dev.arabicalphabet.BuildConfig

class SharedRepository {
    companion object{
        private  fun getPreferences(): SharedPreferences {
            return App.getAppInstance().getSharedPreferences("AppPreference", Context.MODE_PRIVATE)
        }

        fun getMaxTestPosotionAlfabet():Int{
                return  if(BuildConfig.DEBUG)return 2
                else return 15
        }

        fun setAlarmTime(long: Long){
            getPreferences().edit().putLong("reminder",long).apply()
        }
        fun getAlarmTime():Long{
           return getPreferences().getLong("reminder",62167449726253)
        }
        fun isAlarm():Boolean{
            return getPreferences().getBoolean("isAlarm",false)
        }
        fun setIsAlarm(boolean: Boolean){
            getPreferences().edit().putBoolean("isAlarm",boolean).apply()
        }
        fun getStartPracticeIdentify():String{
            if(BuildConfig.DEBUG)return "ca-app-pub-3940256099942544/1033173712"
            else return "ca-app-pub-8352837786919066/6449150906"
        }

        fun getVideoReclamIdentify():String{
            if(BuildConfig.DEBUG)return "ca-app-pub-3940256099942544/5224354917"
            else return "ca-app-pub-8352837786919066/9917622628"
        }

    }



}

