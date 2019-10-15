package id.editya.thesubmissionbe.remider


import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.*
import id.editya.thesubmissionbe.Utils


class NotificationUtils {

    fun setNotification(activity: Activity, type: String) {

        val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java)

        if (type == Utils.KEY_RELEASE_REMINDER) {

            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra(Utils.FROM, Utils.KEY_RELEASE_REMINDER)

            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 8)
                set(Calendar.MINUTE, 0)
            }

            val pendingIntent =
                PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        } else {

            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra(Utils.FROM, Utils.KEY_DAILY_REMINDER)

            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 7)
                set(Calendar.MINUTE,0)
            }

            val pendingIntent =
                PendingIntent.getBroadcast(activity, 1, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }


    }

    fun offNotification(activity: Activity, type: String) {

            val aManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val intent = Intent(activity.applicationContext, AlarmReceiver::class.java)
        if (type == Utils.KEY_RELEASE_REMINDER) {
            val pIntent = PendingIntent.getBroadcast(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            aManager.cancel(pIntent)

        } else {
            val pIntent = PendingIntent.getBroadcast(activity, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            aManager.cancel(pIntent)
        }

    }
}