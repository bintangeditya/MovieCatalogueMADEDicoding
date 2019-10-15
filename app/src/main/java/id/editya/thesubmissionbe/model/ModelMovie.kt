package id.editya.thesubmissionbe.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "fav_movie")
@Parcelize
data class ModelMovie(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @Expose
    @SerializedName("id")
    val Id: Int,

    @ColumnInfo(name = "vote_average")
    @Expose
    @SerializedName("vote_average")
    val VoteAverage: Double,

    @ColumnInfo(name = "title")
    @Expose
    @SerializedName("title")
    val Title: String?,

    @ColumnInfo(name = "poster_path")
    @Expose
    @SerializedName("poster_path")
    val PosterPath: String?,

    @ColumnInfo(name = "original_language")
    @Expose
    @SerializedName("original_language")
    val OriginalLanguage: String?,

    @ColumnInfo(name = "backdrop_path")
    @Expose
    @SerializedName("backdrop_path")
    val BackdropPath: String?,

    @ColumnInfo(name = "release_date")
    @Expose
    @SerializedName("release_date")
    var ReleaseDate: String,

    @Expose
    @SerializedName("overview")
    val Overview: String,

    @ColumnInfo(name = "overview_id")
    var OverviewId: String?,

    @ColumnInfo(name = "overview_en")
    var OverviewEn: String?,

    @ColumnInfo(name = "fav")
    var Fav: Int?

) : Parcelable