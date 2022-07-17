package com.maverick.timetable

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
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
    private lateinit var name: String
    private lateinit var className: String
    private lateinit var division: String
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        sharedPreferences = getSharedPreferences("mykey", MODE_PRIVATE)
        val isFirstStart = sharedPreferences.getBoolean("firstStart", false)

        if (isFirstStart) {//opens activity only once.
            val intent = Intent(this, Timetable::class.java)
            startActivity(intent)
            finish()
        } else {
            val editor = sharedPreferences.edit()
            editor.putBoolean("firstStart", true).apply()
        }

        moveToTimetable()
        createNotificationChannel()
        setAlarm()
//        if (binding.nameText.text.isNullOrEmpty() || binding.classText.text.isNullOrEmpty() || binding.divisionText.text.isNullOrEmpty())
//            allFieldsFilled()
    }

    private fun allFieldsFilled() {
        binding.next.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Fill all the fields.")
                .setPositiveButton("ok") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .create()
            alertDialog.show()
        }
    }

    private fun moveToTimetable() {
        binding.next.setOnClickListener {
            val intent = Intent(this, Timetable::class.java)
            name = binding.nameText.text.toString()
            className = binding.classText.text.toString()
            division = binding.divisionText.text.toString()
            sharedPreferences = getSharedPreferences("mykey", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("username", name)
            editor.putString("classname", className)
            editor.putString("division", division)
            editor.apply()
            startActivity(intent)
            finish()
        }
    }

    private fun setAlarm() {
        val date = Date()
        calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.add(Calendar.MINUTE, 1)
        Log.d("time", calendar.time.time.toString())

        val thuReq: Long = Calendar.getInstance().timeInMillis + 1
        val requestCode = thuReq.toInt()
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
//        pendingIntent =
//            PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT)
//        Log.d("test1", "alarm")


//        alarmManager.setInexactRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.time.time,
//            30000,
//            pendingIntent
//        )

//        if (binding.divisionText.text.toString() == "A") {
//            Log.d("test1", "alarm")
//            intent.putExtra("notificationTitle", "CS - 123")
//            intent.putExtra("notificationContent", "R maam")
//            pendingIntent =
//                PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT)
//            alarmManager.setExactAndAllowWhileIdle(
//                AlarmManager.RTC_WAKEUP,
//                calendar.time.time + 30000,
//                pendingIntent
//            )
//        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationName = "Lecture Timetable"
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