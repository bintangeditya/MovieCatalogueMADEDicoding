package id.editya.thesubmissionbe.remider

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import id.editya.thesubmissionbe.R
import id.editya.thesubmissionbe.Utils
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelMovie
import java.util.*
import java.text.SimpleDateFormat


class AlarmReceiver : BroadcastReceiver() {

    lateinit var dataSource: DataSource

    @SuppressLint("SimpleDateFormat")
    override fun onReceive(context: Context, intent: Intent) {

        if (intent.getStringExtra(Utils.FROM) == Utils.KEY_RELEASE_REMINDER) {

            dataSource = DataSource(context)

            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("yyyy-MM-dd")
            val formattedDate = df.format(c)

            dataSource.getTodayMovie(object : DataSource.MovieCallBack {
                override fun onSuccessMovie(data: List<ModelMovie>?) {
                    data!!.forEach {
                        val service = Intent(context, NotificationService::class.java)
                        service.putExtra(Utils.NOTIFMOVIE, it)
                        service.putExtra(Utils.FROM, Utils.KEY_RELEASE_REMINDER)
                        service.putExtra("reason", intent.getStringExtra("reason"))
                        context.startService(service)
                    }
                }

                override fun onFailureMovie(message: String?) {
                }

            }, context.resources.getString(R.string.code_lang), formattedDate)


        } else {
            val service = Intent(context, NotificationService::class.java)
            service.putExtra(Utils.FROM, Utils.KEY_RELEASE_REMINDER)
            service.putExtra("reason", intent.getStringExtra("reason"))
            context.startService(service)
        }

    }

}