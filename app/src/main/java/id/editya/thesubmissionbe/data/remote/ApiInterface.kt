package id.editya.thesubmissionbe.data.remote

import id.editya.thesubmissionbe.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET("/3/discover/movie?api_key=e460733f73eb3bf893b9ea60206cb616")
    fun getMovie(@Query("language") language: String): Call<ResponseMovie>

    @GET("/3/discover/tv?api_key=e460733f73eb3bf893b9ea60206cb616")
    fun getTVShow(@Query("language") language: String): Call<ResponseTVShow>

    @GET("/3/movie/{id}?api_key=e460733f73eb3bf893b9ea60206cb616")
    fun getOverviewMovie(@Path("id") id:String, @Query("language") language: String):Call<ModelMovie>

    @GET("/3/tv/{id}?api_key=e460733f73eb3bf893b9ea60206cb616")
    fun getOverviewTVShow(@Path("id") id:String, @Query("language") language: String):Call<ModelTVShow>

    @GET("/3/search/movie?api_key=e460733f73eb3bf893b9ea60206cb616")
    fun searchMovie(@Query("language")language: String, @Query("query") query : String ) : Call<ResponseMovie>

    @GET("/3/search/tv?api_key=e460733f73eb3bf893b9ea60206cb616")
    fun searchTVShow(@Query("language")language: String, @Query("query") query : String ) : Call<ResponseTVShow>

    @GET("/3/discover/movie?api_key=e460733f73eb3bf893b9ea60206cb616")
    fun getMovieToday(@Query("language")language: String,@Query("primary_release_date.gte") date : String) : Call<ResponseMovie>
}


