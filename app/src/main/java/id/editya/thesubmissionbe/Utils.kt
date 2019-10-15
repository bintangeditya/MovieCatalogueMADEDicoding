package id.editya.thesubmissionbe


import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast
import android.content.Context.MODE_PRIVATE
import android.app.Activity
import android.content.SharedPreferences


class Utils {

    companion object {
        const val INTENTDATA = "INTENTDATA"
        const val FROM = "FROM"
        const val FROMFAV = "FROMFAV"
        const val POSITION = "POSITION"
        const val STATE = "STATE"
        const val MY_PREFS_NAME = "MY_PREFS_NAME"
        const val KEY_RELEASE_REMINDER = "KEY_RELEASE_REMINDER"
        const val KEY_DAILY_REMINDER = "KEY_DAILY_REMINDER"
        const val NOTIFMOVIE = "NOTIFMOVIE"
        private lateinit var prefs : SharedPreferences

        fun makeSharedPreferences(activity: Activity) {
            prefs = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
            prefs.edit().apply()
        }

        fun putSharedPreferences(key:String,value : String){
            prefs.edit().putString(key,value).commit()
        }

        fun getSharedPreferences(key : String):String?{
            return prefs.getString(key,"0")
        }

        fun showToast(context: Context, text: String, durationInMillis: Int) {
            val mToastToShow = Toast.makeText(context, text, Toast.LENGTH_LONG)
            val toastCountDown: CountDownTimer
            toastCountDown = object : CountDownTimer(durationInMillis.toLong(), 100) {
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    mToastToShow.cancel()
                }
            }
            mToastToShow.show()
            toastCountDown.start()
        }

    }
}

