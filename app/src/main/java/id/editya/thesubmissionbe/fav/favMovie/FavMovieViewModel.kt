package id.editya.thesubmissionbe.fav.favMovie

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.content.Context
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelMovie

class FavMovieViewModel : ViewModel() {
    val listFavMovie = ArrayList<ModelMovie>()
    private lateinit var dataSource : DataSource
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    fun setCtx(context: Context){
        this.context = context
        dataSource = DataSource(context)
    }

    fun getFavMovie(callback : DataSource.MovieCallBack){
        dataSource.getFavMovie(object : DataSource.MovieCallBack{
            override fun onSuccessMovie(data: List<ModelMovie>?) {
                listFavMovie.clear()
                listFavMovie.addAll(data!!)
                callback.onSuccessMovie(data)
            }

            override fun onFailureMovie(message: String?) {
                callback.onFailureMovie(message)
            }

        })
    }

    fun delete(data: ModelMovie,p:Int){
        dataSource.deleteFavMovie(data)
        listFavMovie.removeAt(p)
    }
}