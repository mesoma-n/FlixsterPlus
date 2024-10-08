package com.example.flixsterplus

import com.google.gson.annotations.SerializedName

class FlixsterMovie {

    @JvmField
    @SerializedName("title")
    var title: String? = null
    @SerializedName("poster_path")
    var movieImageUrl: String? = null
    @SerializedName("backdrop_path")
    var posterImageUrl: String? = null
    @SerializedName("overview")
    var description: String? = null
    @SerializedName("popularity")
    var popular: Double? = null
    @SerializedName("release_date")
    var release: String? = null
}