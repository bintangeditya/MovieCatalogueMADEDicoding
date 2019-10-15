package id.editya.thesubmissionbe.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.database.Cursor
import android.view.Menu
import id.editya.thesubmissionbe.model.ModelMovie
import retrofit2.http.DELETE


@Dao
interface MovieDao {

    @Query("SELECT * FROM fav_movie")
    fun getListMovie(): List<ModelMovie>

    @Insert
    fun addFavMovie(movie : ModelMovie)

    @Query("SELECT COUNT(*) FROM fav_movie WHERE id = :id")
    fun checkMovie(id : String): Int

    @Delete
    fun deleteMovie(movie: ModelMovie)

    @Query("SELECT * FROM fav_movie")
    fun selectAll(): Cursor

    @Query("SELECT * FROM fav_movie WHERE id = :id")
    fun selectById(id: Long): Cursor
}