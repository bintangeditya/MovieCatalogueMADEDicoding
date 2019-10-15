package id.editya.thesubmissionbe.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseMovie(
    @Expose
    @SerializedName("results")
    val result: List<ModelMovie>,

    @Expose
    @SerializedName("total_results")
    val totalResult: Int
)