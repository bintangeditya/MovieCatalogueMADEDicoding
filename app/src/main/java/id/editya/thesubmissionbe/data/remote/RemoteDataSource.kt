package id.editya.thesubmissionbe.data.remote

import android.util.Log
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelMovie
import id.editya.thesubmissionbe.model.ModelTVShow
import id.editya.thesubmissionbe.model.ResponseMovie
import id.editya.thesubmissionbe.model.ResponseTVShow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    private var apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)

    fun getMovie(callback: DataSource.MovieCallBack, codeLang: String) {

        val call: Call<ResponseMovie> = apiInterface.getMovie(codeLang)
        call.enqueue(object : Callback<ResponseMovie> {
            override fun onResponse(call: Call<ResponseMovie>, response: Response<ResponseMovie>) {
                callback.onSuccessMovie(response.body()?.result)
            }

            override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                callback.onFailureMovie(t.toString())
            }
        })

    }

    fun getTodayMovie(callback: DataSource.MovieCallBack, codeLang: String, date : String){
        val call: Call<ResponseMovie> = apiInterface.getMovieToday(codeLang,date)
        call.enqueue(object : Callback<ResponseMovie> {
            override fun onResponse(call: Call<ResponseMovie>, response: Response<ResponseMovie>) {
                callback.onSuccessMovie(response.body()?.result)
            }
            override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                callback.onFailureMovie(t.toString())
            }
        })
    }


    fun getTVShow(callback: DataSource.TVShowCallBack, codeLang: String) {

        val call: Call<ResponseTVShow> = apiInterface.getTVShow(codeLang)
        call.enqueue(object : Callback<ResponseTVShow> {
            override fun onResponse(call: Call<ResponseTVShow>, response: Response<ResponseTVShow>) {
                callback.onSuccessTVShow(response.body()?.result)
            }

            override fun onFailure(call: Call<ResponseTVShow>, t: Throwable) {
                callback.onFailureTVShow(t.toString())
            }
        })

    }




    fun getOverviewMovie(callback: DataSource.OverviewMovieCallback, id : String){

        Log.d("devdevhaha","getOverviewMovie")
        val oMovies = ArrayList<String>()

        val callEn : Call<ModelMovie> = apiInterface.getOverviewMovie(id,"en-US")
        callEn.enqueue(object : Callback<ModelMovie>{
            override fun onResponse(call: Call<ModelMovie>, response: Response<ModelMovie>) {
                oMovies.add(response.body()!!.Overview)
                Log.d("devdevhaha","response"+response.body()!!.Overview)
                Log.d("devdevhaha","getOverviewMovie,onResponse,en")

                val callId : Call<ModelMovie> = apiInterface.getOverviewMovie(id,"id-ID")
                callId.enqueue(object : Callback<ModelMovie>{
                    override fun onResponse(call: Call<ModelMovie>, response: Response<ModelMovie>) {
                        oMovies.add(response.body()!!.Overview)
                        Log.d("devdevhaha","response"+response.body()!!.Overview)
                        Log.d("devdevhaha","getOverviewMovie,onResponse,in")
                        callback.onSuccessMovie(oMovies)
                    }
                    override fun onFailure(call: Call<ModelMovie>, t: Throwable) {
                        oMovies.add("(tidak ada ikhtisar dalam bahasa Indonesia)")
                        Log.d("devdevhaha","getOverviewMovie,onFailureTVShow,in")
                        callback.onSuccessMovie(oMovies)
                    }

                })
            }
            override fun onFailure(call: Call<ModelMovie>, t: Throwable) {
                Log.d("devdevhaha","getOverviewMovie,onFailureTVShow,en")
                oMovies.add("(there is no overview in English)")

                val callId : Call<ModelMovie> = apiInterface.getOverviewMovie(id,"id-ID")
                callId.enqueue(object : Callback<ModelMovie>{
                    override fun onResponse(call: Call<ModelMovie>, response: Response<ModelMovie>) {
                        oMovies.add(response.body()!!.Overview)
                        Log.d("devdevhaha","response"+response.body()!!.Overview)
                        Log.d("devdevhaha","getOverviewMovie,onResponse,in")
                        callback.onSuccessMovie(oMovies)
                    }
                    override fun onFailure(call: Call<ModelMovie>, t: Throwable) {
                        oMovies.add("(tidak ada ikhtisar dalam bahasa Indonesia)")
                        Log.d("devdevhaha","getOverviewMovie,onFailureTVShow,in")
                        callback.onSuccessMovie(oMovies)
                    }

                })
            }

        })

    }

    fun getOverviewTVShow(callback: DataSource.OverviewTVShowCallback, id : String){

        Log.d("devdevhaha","getOverviewTVShow")
        val oTVShows = ArrayList<String>()

        val callEn : Call<ModelTVShow> = apiInterface.getOverviewTVShow(id,"en-US")
        callEn.enqueue(object : Callback<ModelTVShow>{
            override fun onResponse(call: Call<ModelTVShow>, response: Response<ModelTVShow>) {
                oTVShows.add(response.body()!!.Overview)
                Log.d("devdevhaha","response"+response.body()!!.Overview)
                Log.d("devdevhaha","getOverviewTVShow,onResponse,en")


                val callId : Call<ModelTVShow> = apiInterface.getOverviewTVShow(id,"id-ID")
                callId.enqueue(object : Callback<ModelTVShow>{
                    override fun onResponse(call: Call<ModelTVShow>, response: Response<ModelTVShow>) {
                        oTVShows.add(response.body()!!.Overview)
                        Log.d("devdevhaha","response"+response.body()!!.Overview)
                        Log.d("devdevhaha","getOverviewTVShow,onResponse,in")
                        callback.onSuccessTVShow(oTVShows)

                    }
                    override fun onFailure(call: Call<ModelTVShow>, t: Throwable) {
                        oTVShows.add("(tidak ada ikhtisar dalam bahasa Indonesia)")
                        Log.d("devdevhaha","getOverviewTVShow,onFailureTVShow,in")
                        callback.onSuccessTVShow(oTVShows)

                    }

                })
            }
            override fun onFailure(call: Call<ModelTVShow>, t: Throwable) {
                oTVShows.add("(there is no overview in English)")
                Log.d("devdevhaha","getOverviewTVShow,onFailureTVShow,en")


                val callId : Call<ModelTVShow> = apiInterface.getOverviewTVShow(id,"id-ID")
                callId.enqueue(object : Callback<ModelTVShow>{
                    override fun onResponse(call: Call<ModelTVShow>, response: Response<ModelTVShow>) {
                        oTVShows.add(response.body()!!.Overview)
                        Log.d("devdevhaha","response"+response.body()!!.Overview)
                        Log.d("devdevhaha","getOverviewTVShow,onResponse,in")
                        callback.onSuccessTVShow(oTVShows)

                    }
                    override fun onFailure(call: Call<ModelTVShow>, t: Throwable) {
                        oTVShows.add("(tidak ada ikhtisar dalam bahasa Indonesia)")
                        Log.d("devdevhaha","getOverviewTVShow,onFailureTVShow,in")
                        callback.onSuccessTVShow(oTVShows)
                    }

                })
            }

        })

    }

    fun searchMovie(callback: DataSource.SearchMovieCallBack, codeLang: String, query : String) {
        try {
            val call: Call<ResponseMovie> = apiInterface.searchMovie(codeLang,query)
            call.enqueue(object : Callback<ResponseMovie> {
                override fun onResponse(call: Call<ResponseMovie>, response: Response<ResponseMovie>) {
                    val result = ArrayList<ModelMovie>()
                    var size = 0
                    if (!response.body()?.result.isNullOrEmpty()) {
                        result.addAll(response?.body()!!.result)
                        size = response.body()?.totalResult!!
                    }
                    callback.onSuccessMovie(result,size)
                }

                override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                    callback.onFailureMovie(t.toString())
                }
            })

        } catch (e: Exception) {
        }

    }


    fun searchTVShow(callback: DataSource.SearchTVShowCallBack, codeLang: String , query : String) {
        try {

            val call: Call<ResponseTVShow> = apiInterface.searchTVShow(codeLang, query)
            call.enqueue(object : Callback<ResponseTVShow> {
                override fun onResponse(call: Call<ResponseTVShow>, response: Response<ResponseTVShow>) {
                    val result = ArrayList<ModelTVShow>()
                    var size = 0
                    if (!response.body()?.result.isNullOrEmpty()) {
                        result.addAll(response?.body()!!.result)
                        size = response.body()?.totalResult!!
                    }

                    callback.onSuccessTVShow(result,size)
                }

                override fun onFailure(call: Call<ResponseTVShow>, t: Throwable) {
                    callback.onFailureTVShow(t.toString())
                }
            })
        }catch (e : Exception){}

    }

}