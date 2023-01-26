package com.example.mdlesson.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaDayPictureRetrofit {

    private val baseUrl = "https://api.nasa.gov/"

    fun getNasaDayPictureService() : NasaDayPictureService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NasaDayPictureService::class.java)
    }

    private fun createOkHttpClient() : OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
        httpClient.addInterceptor(loggingInterceptor)
        return httpClient.build()
    }
}