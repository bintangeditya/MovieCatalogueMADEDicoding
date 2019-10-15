package id.editya.thesubmissionbe.data.local

import android.content.Context
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelMovie
import id.editya.thesubmissionbe.model.ModelTVShow

class LocalBeDataSource(context: Context) {
    private var movieDao: MovieDao = BeDataBase.getInstance(context).movieDao()
    private var tvshowDao: TvshowDao = BeDataBase.getInstance(context).tvshowDao()

    fun getFavMovie(callback: DataSource.MovieCallBack) {
        val runnable = Runnable {
            val movie = movieDao.getListMovie()
            if (movie.isNotEmpty())
                callback.onSuccessMovie(movie)
            else callback.onFailureMovie("data is empty or something happen, something bad -_-")
        }

        Thread(runnable).start()
    }

    fun getFavTvshow(callback: DataSource.TVShowCallBack) {
        val runnable = Runnable {

            val tvShow = tvshowDao.getListTvshow()
            if (tvShow.isNotEmpty())
                callback.onSuccessTVShow(tvShow)
            else callback.onFailureTVShow("data is empty or something happen, something bad -_-")
        }

        Thread(runnable).start()
    }

    fun getFavMovieForWidget(callback: DataSource.MovieCallBack) {
        val movie = movieDao.getListMovie()
        if (movie.isNotEmpty())
            callback.onSuccessMovie(movie)
        else callback.onFailureMovie("data is empty or something happen, something bad -_-")

    }

    fun getFavTvshowForWidget(callback: DataSource.TVShowCallBack) {
        val tvShow = tvshowDao.getListTvshow()
        if (tvShow.isNotEmpty())
            callback.onSuccessTVShow(tvShow)
        else callback.onFailureTVShow("data is empty or something happen, something bad -_-")
    }

    fun saveFavMovie(movie: ModelMovie) {
        val runnable = Runnable {
            movieDao.addFavMovie(movie)
        }
        Thread(runnable).start()
    }

    fun saveFavTvshow(tvShow: ModelTVShow) {
        val runnable = Runnable {
            tvshowDao.addFavTvshow(tvShow)
        }
        Thread(runnable).start()
    }

    fun deleteFavMovie(movie: ModelMovie) {
        val runnable = Runnable {
            movieDao.deleteMovie(movie)
        }
        Thread(runnable).start()
    }

    fun deleteFavTvshow(tvShow: ModelTVShow) {
        val runnable = Runnable {
            tvshowDao.deleteTvshow(tvShow)
        }
        Thread(runnable).start()
    }

    fun checkFavMovie(id: String, callback: DataSource.CheckMovieCallback) {
        val runnable = Runnable {
            val r = movieDao.checkMovie(id)
            callback.onResult(r)
        }
        Thread(runnable).start()

    }

    fun checkFavTvshow(id: String, callback: DataSource.CheckTvshowCallback) {
        val runnable = Runnable {
            val r = tvshowDao.checkTvshow(id)
            callback.onResult(r)
        }
        Thread(runnable).start()

    }
}