package id.editya.thesubmissionbe.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import id.editya.thesubmissionbe.Utils.Companion.FROM
import id.editya.thesubmissionbe.model.ModelMovie
import id.editya.thesubmissionbe.model.ModelTVShow
import retrofit2.http.DELETE

@Dao
interface TvshowDao {
    @Query("SELECT * FROM fav_tvshow")
    fun getListTvshow(): List<ModelTVShow>

    @Insert
    fun addFavTvshow(tvshow : ModelTVShow)

    @Query("SELECT COUNT(*) FROM fav_tvshow WHERE id = :id")
    fun checkTvshow(id : String): Int

    @Delete
    fun deleteTvshow(tvshow: ModelTVShow)
}