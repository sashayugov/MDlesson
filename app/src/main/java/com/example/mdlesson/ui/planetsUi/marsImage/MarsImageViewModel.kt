package com.example.mdlesson.ui.planetsUi.marsImage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mdlesson.BuildConfig
import com.example.mdlesson.retrofit.NasaDayPictureRetrofit
import com.example.mdlesson.retrofit.model.NasaResponseMarsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsImageViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MarsImage> = MutableLiveData(),
    private val retrofitImpl: NasaDayPictureRetrofit = NasaDayPictureRetrofit()
) : ViewModel() {

    fun getMarsImageData(): LiveData<MarsImage> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = MarsImage.Loading(null)

        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            MarsImage.Error(Throwable("You need API key"))
        } else {
            executeEarthImageRequest(apiKey)
        }
    }

    private fun executeEarthImageRequest(apiKey: String) {
        val callback = object : Callback<NasaResponseMarsModel> {
            override fun onResponse(
                call: Call<NasaResponseMarsModel>,
                response: Response<NasaResponseMarsModel>
            ) {
                handleMarsImageResponse(response)
            }

            override fun onFailure(call: Call<NasaResponseMarsModel>, t: Throwable) {
                liveDataForViewToObserve.value = MarsImage.Error(t)
            }
        }
        retrofitImpl.getNasaDayPictureService().getMarsImage(apiKey).enqueue(callback)
    }

    private fun handleMarsImageResponse(response: Response<NasaResponseMarsModel>) {
        if (response.isSuccessful && response.body() != null) {
            liveDataForViewToObserve.value = MarsImage.Success(response.body()!!)
        }

        val message = response.message()
        if (message.isNullOrEmpty()) {
            liveDataForViewToObserve.value = MarsImage.Error(Throwable("Unidentified error"))
        } else {
            liveDataForViewToObserve.value = MarsImage.Error(Throwable(message))
        }
    }
}