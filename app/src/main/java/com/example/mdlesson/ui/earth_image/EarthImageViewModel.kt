package com.example.mdlesson.ui.earth_image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mdlesson.BuildConfig
import com.example.mdlesson.retrofit.NasaDayPictureRetrofit
import com.example.mdlesson.retrofit.NasaResponseEarthModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthImageViewModel(
    private val liveDataForViewToObserve: MutableLiveData<EarthImage> = MutableLiveData(),
    private val retrofitImpl: NasaDayPictureRetrofit = NasaDayPictureRetrofit()
) : ViewModel() {

    fun getEarthImageData(): LiveData<EarthImage> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = EarthImage.Loading(null)

        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            EarthImage.Error(Throwable("You need API key"))
        } else {
            executeEarthImageRequest(apiKey)
        }
    }

    private fun executeEarthImageRequest(apiKey: String) {
        val callback = object : Callback<NasaResponseEarthModel> {
            override fun onResponse(
                call: Call<NasaResponseEarthModel>,
                response: Response<NasaResponseEarthModel>
            ) {
                handleEarthImageResponse(response)
            }

            override fun onFailure(call: Call<NasaResponseEarthModel>, t: Throwable) {
                liveDataForViewToObserve.value = EarthImage.Error(t)
            }
        }
        retrofitImpl.getNasaDayPictureService().getEarthImage(apiKey).enqueue(callback)
    }

    private fun handleEarthImageResponse(response: Response<NasaResponseEarthModel>) {
        if (response.isSuccessful && response.body() != null) {
            liveDataForViewToObserve.value = EarthImage.Success(response.body()!!)
        }

        val message = response.message()
        if (message.isNullOrEmpty()) {
            liveDataForViewToObserve.value = EarthImage.Error(Throwable("Unidentified error"))
        } else {
            liveDataForViewToObserve.value = EarthImage.Error(Throwable(message))
        }
    }
}