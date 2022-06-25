package com.maverick.timetable

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.maverick.timetable.databinding.ActivityTimetableBinding
import java.util.*

class Timetable : AppCompatActivity() {

    private lateinit var binding: ActivityTimetableBinding;
    private lateinit var alarmManager: AlarmManager
    private lateinit var calendar: Calendar
    private lateinit var pendingIntent: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timetable)
        val sharedPreferences = getSharedPreferences("mykey", MODE_PRIVATE)
        val name = sharedPreferences.getString("username", "")
        val division = sharedPreferences.getString("division", "")
        val className = sharedPreferences.getString("classname", "")
        binding.name.text = "Welcome " + name + "\n" + className + "\n" + division
        binding.profile.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }

        val date = Date()
        calendar = Calendar.getInstance()
        calendar.time = date
//        calendar.set(Calendar.DAY_OF_WEEK,1)
//        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.add(Calendar.MINUTE, 0)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

//        calendar.add(Calendar.HOUR_OF_DAY, 20)
//        calendar.add(Calendar.SECOND, 0)
        Log.d("set", calendar.get(Calendar.HOUR_OF_DAY).toString())
        Log.d("set", calendar.get(Calendar.MINUTE).toString())
        Log.d("set", calendar.get(Calendar.SECOND).toString())

//        val thuReq: Long = Calendar.getInstance().timeInMillis + 1
//        val requestCode = thuReq.toInt()
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        if (division == "A") {
            calendar.set(Calendar.HOUR_OF_DAY, 13)
            calendar.set(Calendar.MINUTE, 55)
            if (calendar.get(Calendar.HOUR_OF_DAY) >= currentHour && calendar.get(Calendar.MINUTE) >= currentMinute) {
                Log.d("set", calendar.get(Calendar.HOUR_OF_DAY).toString())
                Log.d("set", calendar.get(Calendar.MINUTE).toString())
                Log.d("test1", calendar.timeInMillis.toString())
                intent.putExtra("notificationTitle", "CS - 3610")
                intent.putExtra("notificationContent", "Prof.Kamakshi Goel (Testing tools)")
                pendingIntent =
                    PendingIntent.getBroadcast(
                        this,
                        calendar.timeInMillis.toInt(),
                        intent,
                        PendingIntent.FLAG_ONE_SHOT
                    )
                setAlarm()
            }
            calendar.set(Calendar.HOUR_OF_DAY, 14)
            calendar.set(Calendar.MINUTE, 55)
            if (calendar.get(Calendar.HOUR_OF_DAY) >= currentHour && calendar.get(Calendar.MINUTE) >= currentMinute) {
                Log.d("set", calendar.get(Calendar.HOUR_OF_DAY).toString())
                Log.d("set", calendar.get(Calendar.MINUTE).toString())
                Log.d("test2", calendar.time.time.toString() + 1)
                intent.putExtra("notificationTitle", "CS - 361")
                intent.putExtra("notificationContent", "Prof.Nutan Borse (Operating system)")
                pendingIntent =
                    PendingIntent.getBroadcast(
                        this,
                        calendar.time.time.toInt() + 1,
                        intent,
                        PendingIntent.FLAG_ONE_SHOT
                    )
                setAlarm()
            }
            calendar.set(Calendar.HOUR_OF_DAY, 15)
            calendar.set(Calendar.MINUTE, 55)
            if (calendar.get(Calendar.HOUR_OF_DAY) >= currentHour && calendar.get(Calendar.MINUTE) >= currentMinute) {
                Log.d("set", calendar.get(Calendar.HOUR_OF_DAY).toString())
                Log.d("set", calendar.get(Calendar.MINUTE).toString())
                Log.d("test2", calendar.time.time.toString() + 2)
                intent.putExtra("notificationTitle", "CS - 364")
                intent.putExtra("notificationContent", "Prof.Renna Bharathi (Data Analytics)")
                pendingIntent =
                    PendingIntent.getBroadcast(
                        this,
                        calendar.time.time.toInt() + 2,
                        intent,
                        PendingIntent.FLAG_ONE_SHOT
                    )
                setAlarm()
            }
            calendar.set(Calendar.HOUR_OF_DAY, 16)
            calendar.set(Calendar.MINUTE, 55)
            if (calendar.get(Calendar.HOUR_OF_DAY) >= currentHour && calendar.get(Calendar.MINUTE) >= currentMinute) {
                Log.d("set", calendar.get(Calendar.HOUR_OF_DAY).toString())
                Log.d("set", calendar.get(Calendar.MINUTE).toString())
                Log.d("test2", calendar.time.time.toString() + 3)
                intent.putExtra("notificationTitle", "CS - 365")
                intent.putExtra("notificationContent", "Prof.Dipali Patole (Java)")
                pendingIntent =
                    PendingIntent.getBroadcast(
                        this,
                        calendar.time.time.toInt() + 3,
                        intent,
                        PendingIntent.FLAG_ONE_SHOT
                    )
                setAlarm()
            } else {
                calendar.add(Calendar.HOUR_OF_DAY, 1)
            }
        }
    }

    private fun setAlarm() {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.time.time + 30000,
            pendingIntent
        )
    }

}
