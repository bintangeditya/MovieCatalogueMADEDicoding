package id.editya.thesubmissionbe.movie

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.content.Context
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelMovie
import java.util.ArrayList

class MovieViewModel : ViewModel() {
    val listMovie = ArrayList<ModelMovie>()
    lateinit var langOfList: String
    @SuppressLint("StaticFieldLeak")
    var context: Context?=null
    private var dataSource: DataSource?=null

    fun setCtx(context: Context){
        this.context=context
        dataSource = DataSource(context)
    }

    fun getMovie(callback: DataSource.MovieCallBack, codeLang: String) {
        try {

            dataSource!!.getMovie(
                object : DataSource.MovieCallBack {
                    override fun onSuccessMovie(data: List<ModelMovie>?) {
                        listMovie.clear()
                        listMovie.addAll(data as ArrayList)
                        langOfList = codeLang
                        callback.onSuccessMovie(listMovie)
                    }

                    override fun onFailureMovie(message: String?) {
                        callback.onFailureMovie(message)
                    }
                }, codeLang
            )
        } catch (e: Exception) {
        }
    }

}