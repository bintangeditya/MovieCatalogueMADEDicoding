package id.editya.thesubmissionbe.data

import android.content.Context
import id.editya.thesubmissionbe.data.remote.RemoteDataSource
import id.editya.thesubmissionbe.model.ModelMovie
import id.editya.thesubmissionbe.model.ModelTVShow
import id.editya.thesubmissionbe.data.local.LocalBeDataSource


class DataSource(context: Context) {

    private val remoteDataSource: RemoteDataSource = RemoteDataSource()
    private val localDataSource : LocalBeDataSource = LocalBeDataSource(context)


    interface MovieCallBack {
        fun onSuccessMovie(data: List<ModelMovie>?)
        fun onFailureMovie(message: String?)
    }

    fun getMovie(callBack: MovieCallBack, codeLang: String) {
        try {
            remoteDataSource.getMovie(object : MovieCallBack {
                override fun onSuccessMovie(data: List<ModelMovie>?) {
                    callBack.onSuccessMovie(data)
                }

                override fun onFailureMovie(message: String?) {
                    callBack.onFailureMovie(message)
                }

            }, codeLang)

        } catch (e: Exception) {
        }
    }


    fun getTodayMovie(callBack: MovieCallBack, codeLang: String, date : String) {
        try {
            remoteDataSource.getTodayMovie(object : MovieCallBack {
                override fun onSuccessMovie(data: List<ModelMovie>?) {
                    callBack.onSuccessMovie(data)
                }

                override fun onFailureMovie(message: String?) {
                    callBack.onFailureMovie(message)
                }

            }, codeLang,date)

        } catch (e: Exception) {
        }
    }

    interface TVShowCallBack {
        fun onSuccessTVShow(data: List<ModelTVShow>?)
        fun onFailureTVShow(message: String?)
    }

    fun getTVShow(callBack: TVShowCallBack, codeLang: String) {
        try {
            remoteDataSource.getTVShow(object : TVShowCallBack {
                override fun onSuccessTVShow(data: List<ModelTVShow>?) {
                    callBack.onSuccessTVShow(data)
                }

                override fun onFailureTVShow(message: String?) {
                    callBack.onFailureTVShow(message)
                }

            }, codeLang)

        } catch (e: Exception) {
        }
    }

    interface OverviewMovieCallback {
        fun onSuccessMovie(oMovies: List<String>?)
    }

    fun getOverviewMovie(callback: OverviewMovieCallback, id : String){
        try{
            remoteDataSource.getOverviewMovie(object : OverviewMovieCallback{

                override fun onSuccessMovie(oMovies: List<String>?) {
                    callback.onSuccessMovie(oMovies)
                }

            },id)

        }catch (e: Exception){

        }
    }

    interface OverviewTVShowCallback {
        fun onSuccessTVShow(oTVShows: List<String>?)
    }

    fun getOverviewTVShow(callback: OverviewTVShowCallback, id : String){
        try{
            remoteDataSource.getOverviewTVShow(object : OverviewTVShowCallback{

                override fun onSuccessTVShow(oTVShows: List<String>?) {
                    callback.onSuccessTVShow(oTVShows)
                }

            },id)

        }catch (e: Exception){

        }
    }

    interface SearchMovieCallBack {
        fun onSuccessMovie(data: List<ModelMovie>?,amount:Int)
        fun onFailureMovie(message: String?)
    }

    fun searchMovie(callBack: SearchMovieCallBack, codeLang: String, query : String) {
        try {
            remoteDataSource.searchMovie(object : SearchMovieCallBack {
                override fun onSuccessMovie(data: List<ModelMovie>?,amount: Int) {
                    callBack.onSuccessMovie(data,amount)
                }

                override fun onFailureMovie(message: String?) {
                    callBack.onFailureMovie(message)
                }

            }, codeLang, query)

        } catch (e: Exception) {
        }
    }

    interface SearchTVShowCallBack {
        fun onSuccessTVShow(data: List<ModelTVShow>?, amount:Int)
        fun onFailureTVShow(message: String?)
    }

    fun searchTVShow(callBack: SearchTVShowCallBack, codeLang: String, query : String) {
        try {
            remoteDataSource.searchTVShow(object : SearchTVShowCallBack {
                override fun onSuccessTVShow(data: List<ModelTVShow>?,amount : Int) {
                    callBack.onSuccessTVShow(data,amount)
                }

                override fun onFailureTVShow(message: String?) {
                    callBack.onFailureTVShow(message)
                }

            }, codeLang, query)

        } catch (e: Exception) {
        }
    }

    fun getFavMovie(callBack: MovieCallBack) {
        try {
            localDataSource.getFavMovie(object : MovieCallBack {
                override fun onSuccessMovie(data: List<ModelMovie>?) {
                    callBack.onSuccessMovie(data)
                }

                override fun onFailureMovie(message: String?) {
                    callBack.onFailureMovie(message)
                }

            })

        } catch (e: Exception) {

        }
    }

    fun getFavTvshow(callBack: TVShowCallBack) {
        try {
            localDataSource.getFavTvshow(object : TVShowCallBack {
                override fun onSuccessTVShow(data: List<ModelTVShow>?) {
                    callBack.onSuccessTVShow(data)
                }

                override fun onFailureTVShow(message: String?) {
                    callBack.onFailureTVShow(message)
                }

            })

        } catch (e: Exception) {

        }
    }

    fun getFavMovieForWidget(callBack: MovieCallBack) {
        try {
            localDataSource.getFavMovieForWidget(object : MovieCallBack {
                override fun onSuccessMovie(data: List<ModelMovie>?) {
                    callBack.onSuccessMovie(data)
                }

                override fun onFailureMovie(message: String?) {
                    callBack.onFailureMovie(message)
                }

            })

        } catch (e: Exception) {

        }
    }

    fun getFavTvshowForWidget(callBack: TVShowCallBack) {
        try {
            localDataSource.getFavTvshowForWidget(object : TVShowCallBack {
                override fun onSuccessTVShow(data: List<ModelTVShow>?) {
                    callBack.onSuccessTVShow(data)
                }

                override fun onFailureTVShow(message: String?) {
                    callBack.onFailureTVShow(message)
                }

            })

        } catch (e: Exception) {

        }
    }

    fun saveFavMovie(data: ModelMovie) {
        try {
            localDataSource.saveFavMovie(data)
        } catch (e: Exception) {

        }
    }

    fun saveFavTvshow(data: ModelTVShow) {
        try {
            localDataSource.saveFavTvshow(data)
        } catch (e: Exception) {

        }
    }

    fun deleteFavMovie(data: ModelMovie) {
        try {
            localDataSource.deleteFavMovie(data)
        } catch (e: Exception) {

        }
    }

    fun deleteFavTvshow(data: ModelTVShow) {
        try {
            localDataSource.deleteFavTvshow(data)
        } catch (e: Exception) {

        }
    }

    interface CheckMovieCallback{
        fun onResult(r : Int)
    }

    fun checkMovie(id : String, callBack: CheckMovieCallback){
        try{
            localDataSource.checkFavMovie(id,callBack )
        }catch (e : Exception){

        }
    }

    interface CheckTvshowCallback{
        fun onResult(r : Int)
    }

    fun checkTvshow(id : String, callBack: CheckTvshowCallback){
        try{
            localDataSource.checkFavTvshow(id,callBack )
        }catch (e : Exception){

        }
    }



}