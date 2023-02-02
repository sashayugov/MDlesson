package com.example.mdlesson.ui.planetsUi.moonImage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mdlesson.BuildConfig
import com.example.mdlesson.retrofit.NasaDayPictureRetrofit
import com.example.mdlesson.retrofit.model.NasaResponseMoonModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoonImageViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MoonImage> = MutableLiveData(),
    private val retrofitImpl: NasaDayPictureRetrofit = NasaDayPictureRetrofit()
) : ViewModel() {

    fun getMoonImageData(): LiveData<MoonImage> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = MoonImage.Loading(null)

        val apikey = BuildConfig.NASA_API_KEY
        if (apikey.isBlank()) {
            MoonImage.Error(Throwable("You need API key"))
        } else {
            executeMoonImageRequest(apikey)
        }
    }

    private fun executeMoonImageRequest(apikey: String) {
        val callback = object : Callback<NasaResponseMoonModel> {
            override fun onResponse(
                call: Call<NasaResponseMoonModel>,
                response: Response<NasaResponseMoonModel>
            ) {
                handleMoonImageResponse(response)
            }

            override fun onFailure(call: Call<NasaResponseMoonModel>, t: Throwable) {
                liveDataForViewToObserve.value = MoonImage.Error(t)
            }
        }
        retrofitImpl.getNasaDayPictureService().getMoonImage(apikey).enqueue(callback)
    }

    private fun handleMoonImageResponse(response: Response<NasaResponseMoonModel>) {
        if (response.isSuccessful && response.body() != null) {
            liveDataForViewToObserve.value = MoonImage.Success(response.body()!!)
        }

        val message = response.message()
        if (message.isNullOrEmpty()) {
            liveDataForViewToObserve.value = MoonImage.Error(Throwable("Unidentified error"))
        } else {
            liveDataForViewToObserve.value = MoonImage.Error(Throwable(message))
        }
    }
}