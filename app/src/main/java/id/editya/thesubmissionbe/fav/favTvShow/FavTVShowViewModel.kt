package id.editya.thesubmissionbe.fav.favTvShow

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.content.Context
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelTVShow

class FavTVShowViewModel : ViewModel() {
    val listFavTVShow = ArrayList<ModelTVShow>()
    private lateinit var dataSource : DataSource
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    fun setCtx(context: Context){
        this.context = context
        dataSource = DataSource(context)
    }

    fun getFavTVShow(callback : DataSource.TVShowCallBack){
        dataSource.getFavTvshow(object : DataSource.TVShowCallBack{
            override fun onSuccessTVShow(data: List<ModelTVShow>?) {
                listFavTVShow.clear()
                listFavTVShow.addAll(data!!)
                callback.onSuccessTVShow(data)
            }

            override fun onFailureTVShow(message: String?) {
                callback.onFailureTVShow(message)
            }

        })

    }

    fun delete(data: ModelTVShow,p : Int){
        dataSource.deleteFavTvshow(data)
        listFavTVShow.removeAt(p)
    }
}