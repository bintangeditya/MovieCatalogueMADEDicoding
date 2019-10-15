package id.editya.thesubmissionbe

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import id.editya.thesubmissionbe.data.DataSource


class MainViewModel : ViewModel() {
    var blocked = false
    var state = ""
    lateinit var dataSource: DataSource

    fun injection(context: Context) {
        dataSource =  DataSource(context)
        dataSource.checkTvshow("1",
            object : DataSource.CheckTvshowCallback {
                override fun onResult(r: Int) {
                    Log.d("devdevdev", " injection success")
                }
            })
    }
}