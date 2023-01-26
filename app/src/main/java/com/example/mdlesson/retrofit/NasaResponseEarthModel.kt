package com.example.mdlesson.retrofit

import com.google.gson.annotations.SerializedName

data class NasaResponseEarthModel(

    @SerializedName("url")
    val url: String?
)