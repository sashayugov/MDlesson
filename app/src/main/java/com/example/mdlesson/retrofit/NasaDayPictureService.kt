package com.example.mdlesson.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaDayPictureService {

    @GET("planetary/apod")
    fun getImage(@Query("api_key") apiKey: String): Call<NasaResponseModel>

    @GET("planetary/earth/assets?lat=35.89&lon=27.77&date=2018-06-01")
    fun getEarthImage(@Query("api_key") apiKey: String): Call<NasaResponseEarthModel>

    @GET("mars-photos/api/v1/rovers/curiosity/photos?sol=1000&camera=fhaz")
    fun getMarsImage(@Query("api_key") apiKey: String): Call<NasaResponseMarsModel>

    @GET("planetary/apod?date=2023-01-21")
    fun getMoonImage(@Query("api_key") apiKey: String): Call<NasaResponseMoonModel>
}