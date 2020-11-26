package com.rin_jav_dev.arabicalphabet.main.ui.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.rin_jav_dev.arabicalphabet.R
import com.rin_jav_dev.arabicalphabet.alarm.TimeNotification
import com.rin_jav_dev.arabicalphabet.app.SharedRepository
import kotlinx.android.synthetic.main.fragment_setting.*
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*


class SettingFragment : Fragment() {
    var formater = DecimalFormat("00")
    private lateinit var settingViewModel: SettingViewModel
    var timePickerCalendar = Calendar.getInstance().clone() as Calendar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingViewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
            // val textView: TextView = root.findViewById(R.id.text_home)
        settingViewModel.text.observe(viewLifecycleOwner, Observer {
           // textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        timePickerCalendar.timeInMillis=SharedRepository.getAlarmTime()
        cbDayliReminders.setText(getString(R.string.dayli_reminders)+formater.format(timePickerCalendar.get(Calendar.HOUR_OF_DAY))+":"+  formater.format(timePickerCalendar.get(Calendar.MINUTE)))
        cbDayliReminders.isChecked=SharedRepository.isAlarm()
        cbDayliReminders.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    SharedRepository.setIsAlarm(true)
                    showTimePickerDialog()
                }else{
                    TimeNotification.cancelRemider(context)
                    SharedRepository.setIsAlarm(false)
                }
            }
        })

    }


fun showTimePickerDialog(){
   val timePickerDialog= TimePickerDialog(
        context, TimePickerDialog.OnTimeSetListener { timePickerView, hours, minute ->
            timePickerCalendar = Calendar.getInstance()
            timePickerCalendar.set(Calendar.HOUR_OF_DAY,hours)
            timePickerCalendar.set(Calendar.MINUTE,minute)


            SharedRepository.setAlarmTime(timePickerCalendar.timeInMillis)

           cbDayliReminders.text=getString(R.string.dayli_reminders)+" "+formater.format(timePickerCalendar.get(Calendar.HOUR_OF_DAY))+":"+  formater.format(timePickerCalendar.get(Calendar.MINUTE))

            TimeNotification.createAlarmReminder(timePickerCalendar,context )

        },
        timePickerCalendar.get(Calendar.HOUR_OF_DAY),
        timePickerCalendar.get(Calendar.MINUTE), true
        )
        timePickerDialog.setOnCancelListener(DialogInterface.OnCancelListener {
            cbDayliReminders.isChecked=false
        })
       timePickerDialog.show()
        }




   }


       //----------------------------







