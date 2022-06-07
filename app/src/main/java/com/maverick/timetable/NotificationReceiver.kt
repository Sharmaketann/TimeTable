package com.maverick.timetable

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    private val CHANNEL_ID = "01"
    private val notificationId = 200

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("test","notify success")
        Toast.makeText(context,"it worked.",Toast.LENGTH_SHORT).show()
        val bitmap = BitmapFactory.decodeResource(
            context.applicationContext.resources,
            R.drawable.ti
        )
        val bitmapLargeIcon = BitmapFactory.decodeResource(
            context.applicationContext.resources,
            R.drawable.ti
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val intent1 = Intent(context, Hello::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("CS-123")
                .setContentText("Python lecture")
                .setLargeIcon(bitmap)
//            .setStyle(NotificationCompat.BigPictureStyle().bigLargeIcon(bitmap))
                .setStyle(
                    (NotificationCompat.BigTextStyle().bigText("Python lecture(Kamakshi Goyal)"))
                )
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            val r = RingtoneManager.getRingtone(context, notification)
            r.play()

            with(NotificationManagerCompat.from(context)) {
                notify(notificationId, builder.build())
            }
        }

    }
}