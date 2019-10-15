package id.editya.thesubmissionbe.search

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.content.Context
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelMovie
import id.editya.thesubmissionbe.model.ModelTVShow

class SearchViewModel : ViewModel() {

    var state = "MOVIE"

    val listMovie = ArrayList<ModelMovie>()
    var sizeListMovie = 0
    val listTVShow = ArrayList<ModelTVShow>()
    var sizeListTVShow = 0

    var everHadMovie = false
    var everHadTVShow = false

    var lastQueryMovie = ""
    var lastQueryTVShow = ""
    var lastQuery = ""

    @SuppressLint("StaticFieldLeak")
    var context: Context? = null
    private var dataSource: DataSource? = null

    fun setCtx(context: Context) {
        this.context = context
        dataSource = DataSource(context)
    }

    fun searchMovie(callback: DataSource.SearchMovieCallBack, codeLang: String, query: String) {
        try {

            dataSource!!.searchMovie(
                object : DataSource.SearchMovieCallBack {
                    override fun onSuccessMovie(data: List<ModelMovie>?, amount: Int) {
                        sizeListMovie = amount
                        listMovie.clear()
                        listMovie.addAll(data as java.util.ArrayList)
                        callback.onSuccessMovie(listMovie,amount)
                        lastQueryMovie = query
                        lastQuery = query
                        everHadMovie = true
                    }

                    override fun onFailureMovie(message: String?) {
                        callback.onFailureMovie(message)
                    }
                }, codeLang
                , query
            )
        } catch (e: Exception) {
        }
    }

    fun searchTVShow(callback: DataSource.SearchTVShowCallBack, codeLang: String, query: String) {
        try {

            dataSource!!.searchTVShow(
                object : DataSource.SearchTVShowCallBack {
                    override fun onSuccessTVShow(data: List<ModelTVShow>?, amount: Int) {
                        sizeListTVShow = amount
                        listTVShow.clear()
                        listTVShow.addAll(data as java.util.ArrayList)
                        callback.onSuccessTVShow(listTVShow,amount)
                        lastQueryTVShow = query
                        lastQuery = query
                        everHadTVShow = true
                    }

                    override fun onFailureTVShow(message: String?) {
                        callback.onFailureTVShow(message)
                    }
                }, codeLang
                , query
            )
        } catch (e: Exception) {
        }
    }

}