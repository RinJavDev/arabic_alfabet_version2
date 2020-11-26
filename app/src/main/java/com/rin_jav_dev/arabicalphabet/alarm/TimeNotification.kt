package com.rin_jav_dev.arabicalphabet.alarm


import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.rin_jav_dev.arabicalphabet.Login_Activity
import com.rin_jav_dev.arabicalphabet.R
import java.util.*


class TimeNotification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ME", "Notification started")
       var cal= Calendar.getInstance().clone() as Calendar
cal.timeInMillis= intent.getLongExtra("longTime",0L)
        Log.d("ME", cal.time.toString())
        cal.timeInMillis=System.currentTimeMillis()
        Log.d("ME", cal.time.toString())



        val intentForPending = Intent(context, Login_Activity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0,
            intentForPending, PendingIntent.FLAG_ONE_SHOT
        )
       createNotificaton(context,pendingIntent)

       createAlarmReminder(cal,context)
    }

    fun createNotificaton(
       context: Context
    ,pendingIntent: PendingIntent) {
        val notificationId = Random().nextInt(60000)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //Setting up Notification channels for android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager)
        }
        //val bitmap: Bitmap = getBitmapfromUrl(remoteMessage.getData().get("imageUri"))
        val defaultSoundUri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat.Builder(context, "1998")

                .setSmallIcon(R.mipmap.ic_launcher) //a resource for your custom small icon

                .setContentText(context.getText(R.string.its_practic_time))
                .setAutoCancel(true) //dismisses the notification on click
                .setContentIntent(null)
                .setSound(defaultSoundUri)

    if (pendingIntent != null) notificationBuilder.setContentIntent(pendingIntent)
        println("notificationId is $notificationId")
        notificationManager.notify(
            notificationId /* ID of notification */,
            notificationBuilder.build()
        )
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(notificationManager: NotificationManager?) {
        val att = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        val adminChannelName: CharSequence = "Arabic alfabet"
        val adminChannelDescription = "reminder"
        val adminChannel: NotificationChannel
        adminChannel =
            NotificationChannel("1998", adminChannelName, NotificationManager.IMPORTANCE_LOW)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager?.createNotificationChannel(adminChannel)
    }

companion object{
    fun createAlarmReminder(calendar: Calendar,context: Context?) {

        while (System.currentTimeMillis()>calendar.timeInMillis){
            calendar.add(Calendar.HOUR_OF_DAY,24)

        }
        val time = calendar.time.time
        println("calendar " + calendar.time.time.toString() + " " + System.currentTimeMillis())
        if (System.currentTimeMillis() < time){
            println("calendar bolshe")
        }


        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

        val intent = Intent(context, TimeNotification::class.java)
        intent.putExtra("longTime", time)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager?.cancel(pendingIntent)



        if (Build.VERSION.SDK_INT < 23) {
            if (Build.VERSION.SDK_INT >= 19) {
                if (System.currentTimeMillis() < time)
                    alarmManager!!.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            } else {
                if (System.currentTimeMillis() < time)
                    alarmManager!!.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            }
        } else {
            if (System.currentTimeMillis() < time)
                alarmManager!!.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    pendingIntent
                );
        }
    }

    fun cancelRemider(context: Context?){
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, TimeNotification::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager?.cancel(pendingIntent)
    }
}

}