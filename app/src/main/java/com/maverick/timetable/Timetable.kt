package com.maverick.timetable

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.maverick.timetable.databinding.ActivityTimetableBinding

class Timetable : AppCompatActivity() {
    private val CHANNEL_ID = "01"
    private val notificationId = 200
    lateinit var binding: ActivityTimetableBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timetable)
        val name = intent.getStringExtra("username")
        binding.name.text = "Welcome " + name
        val className = intent.getStringExtra("className")
        val division = intent.getStringExtra("division")

        createNotificationChannel()
//        binding.next.setOnClickListener {
            sendNotification()
//        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationName = "lecture"
            val descriptionText = "Data Analytics Lecture now!"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, notificationName, importance).apply {
                description = descriptionText
            }
//            channel.description = descriptionText

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val intent = Intent(this, Timetable::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val bitmap = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.ti
        )
        val bitmapLargeIcon = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.ti
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("CS-123")
            .setContentText("Python lecture")
            .setLargeIcon(bitmap)
//            .setStyle(NotificationCompat.BigPictureStyle().bigLargeIcon(bitmap))
            .setStyle((NotificationCompat.BigTextStyle().bigText("Python lecture(Kamakshi Goyal)")))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }
}