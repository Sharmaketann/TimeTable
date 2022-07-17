package com.maverick.timetable

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class NotificationReceiver : BroadcastReceiver() {
    private val CHANNEL_ID = "01"
    private var notificationId = System.currentTimeMillis().toInt()
    override fun onReceive(context: Context, int1: Intent) {
        Log.d("test", "notify success")
        Toast.makeText(context, "it worked.", Toast.LENGTH_SHORT).show()
//        val bitmap = BitmapFactory.decodeResource(
//            context.applicationContext.resources,
//            R.drawable.ti
//        )
//        val bitmapLargeIcon = BitmapFactory.decodeResource(
//            context.applicationContext.resources,
//            R.drawable.ti
//        )
        val notificationTitles = int1.getStringExtra("notificationTitle")
        val notificationContent = int1.getStringExtra("notificationContent")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val thuReq: Long = Calendar.getInstance().timeInMillis + 1
            val requestCode = thuReq.toInt()
            val intent = Intent(context, Timetable::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent =
                PendingIntent.getActivity(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(notificationTitles)
//                .setContentText("Python lecture")
//                .setLargeIcon(bitmap)
                .setDefaults(Notification.DEFAULT_ALL)
//            .setStyle(NotificationCompat.BigPictureStyle().bigLargeIcon(bitmap))
                .setStyle(
                    (NotificationCompat.BigTextStyle()
                        .bigText(notificationContent))
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
            builder.setContentIntent(pendingIntent)
//            if (counter <= notificationTitles.size)

//            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
//            val r = RingtoneManager.getRingtone(context, notification)
//            r.play()

            with(NotificationManagerCompat.from(context)) {
                notify(notificationId, builder.build())
                notificationId++
            }
        }

    }
}