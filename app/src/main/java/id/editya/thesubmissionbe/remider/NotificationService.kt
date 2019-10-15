package id.editya.thesubmissionbe.remider

import android.annotation.SuppressLint
import id.editya.thesubmissionbe.R
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.app.NotificationChannel
import id.editya.thesubmissionbe.DetailActivity
import id.editya.thesubmissionbe.MainActivity
import id.editya.thesubmissionbe.Utils
import id.editya.thesubmissionbe.model.ModelMovie


class NotificationService : IntentService("NotificationService") {

    private lateinit var notification: Notification
    private var notifId: Int = 2000

    @SuppressLint("NewApi")
    private fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val context = this.applicationContext
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.parseColor("#e8334a")
            notificationChannel.description = getString(R.string.notification_channel_description)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {

        const val CHANNEL_ID = "id.editya.thesubmissionbe.remider.CHANNEL_ID"
        const val CHANNEL_NAME = "BeFLIX Notification"
    }


    override fun onHandleIntent(intent: Intent?) {
        createChannel()

        lateinit var mTitle: String
        lateinit var mMessage: String
        lateinit var mIntent: Intent

        if (intent!!.getStringExtra(Utils.FROM)!! == Utils.KEY_RELEASE_REMINDER) {

            if (intent.getParcelableExtra<ModelMovie>(Utils.NOTIFMOVIE) != null){
                val movie: ModelMovie = intent.getParcelableExtra(Utils.NOTIFMOVIE)
                mTitle = "Release Today : " + movie.Title
                mMessage = movie.Overview

                mIntent = Intent(this, DetailActivity::class.java)
                mIntent.putExtra(Utils.INTENTDATA, movie)
                mIntent.putExtra(Utils.FROM, "movie")
                mIntent.putExtra(Utils.FROMFAV, "0")
                notifId = movie.Id
            } else {
                mTitle = "BeFlix : Hey Whatsup!!! "
                mMessage = resources.getString(R.string.message_daily)
                mIntent = Intent(this, MainActivity::class.java)
            }

        } else {

            mTitle = "BeFlix : Hey Whatsup!!! "
            mMessage = resources.getString(R.string.message_daily)
            mIntent = Intent(this, MainActivity::class.java)
            notifId = 1414

        }


        val context = this.applicationContext
        var notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifyIntent = mIntent

        val title = mTitle
        val message = mMessage

        notifyIntent.putExtra("title", title)
        notifyIntent.putExtra("message", message)
        notifyIntent.putExtra("notification", true)

        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent = PendingIntent.getActivity(context, 14, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val res = this.resources
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            notification = Notification.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(
                    Notification.BigTextStyle()
                        .bigText(message)
                )
                .setContentText(message).build()
        } else {

            notification = Notification.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(title)
                .setStyle(
                    Notification.BigTextStyle()
                        .bigText(message)
                )
                .setSound(uri)
                .setContentText(message).build()
        }

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notifId, notification)

    }
}