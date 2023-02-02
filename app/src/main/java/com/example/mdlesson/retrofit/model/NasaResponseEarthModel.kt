package com.example.mdlesson.retrofit.model

import com.google.gson.annotations.SerializedName

data class NasaResponseEarthModel(

    @SerializedName("url")
    val url: String?
)