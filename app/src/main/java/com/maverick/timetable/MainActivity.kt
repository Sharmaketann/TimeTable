package com.maverick.timetable

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.maverick.timetable.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "01"
    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmManager: AlarmManager
    private lateinit var calendar: Calendar
    private lateinit var pendingIntent: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        moveToTimetable()
        createNotificationChannel()
        setAlarm()
    }

//    private fun moveToTimetable() {
//        binding.next.setOnClickListener {
//            val name = binding.nameText.text.toString()
//            val className = binding.classText.text.toString()
//            val division = binding.divisionText.text.toString()
//            val intent = Intent(this, Timetable::class.java)
//            intent.putExtra("username", name)
//            intent.putExtra("class", className)
//            intent.putExtra("division", division)
//            startActivity(intent)
//            finish()
//        }
//    }

    private fun setAlarm() {
        val date = Date()
        calendar = Calendar.getInstance()
//        calendar.set(Calendar.HOUR_OF_DAY, 17)
//        calendar.set(Calendar.MINUTE, 22)
//        calendar.set(Calendar.SECOND, 0)
        calendar.time = date
        calendar.add(Calendar.SECOND, 30)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

//        val thuReq: Long = Calendar.getInstance().timeInMillis + 1
//        val reqReqCode = thuReq.toInt()
//        if (calendar.timeInMillis < System.currentTimeMillis()) {
//            calendar.add(Calendar.DAY_OF_YEAR, 1)
//        }
        val intent = Intent(this, NotificationReceiver::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        pendingIntent =
            PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val futureNotificationIn: Long = 5000
        Log.d("test1", "alarm")
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            futureNotificationIn, // 50 minute lecture. 1*60*60*1000
            pendingIntent
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationName = "Lecture TimeTable"
            val descriptionText = "manage notification"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, notificationName, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}