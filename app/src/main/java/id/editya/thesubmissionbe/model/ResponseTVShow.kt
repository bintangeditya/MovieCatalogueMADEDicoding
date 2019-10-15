package id.editya.thesubmissionbe.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseTVShow(
    @Expose
    @SerializedName("results")
    val result: List<ModelTVShow>,

    @Expose
    @SerializedName("total_results")
    val totalResult: Int
)