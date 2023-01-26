package com.example.mdlesson.ui.daily_image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mdlesson.BuildConfig
import com.example.mdlesson.retrofit.NasaDayPictureRetrofit
import com.example.mdlesson.retrofit.NasaResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyImageViewModel(
    private val liveDataForViewToObserve: MutableLiveData<DailyImage> = MutableLiveData(),
    private val retrofitImpl: NasaDayPictureRetrofit = NasaDayPictureRetrofit()
) : ViewModel() {

    fun getImageData(): LiveData<DailyImage> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = DailyImage.Loading(null)

        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            DailyImage.Error(Throwable("You need API key"))
        } else {
            executeImageRequest(apiKey)
        }
    }

    private fun executeImageRequest(apiKey: String) {
        val callback = object : Callback<NasaResponseModel> {
            override fun onResponse(
                call: Call<NasaResponseModel>,
                response: Response<NasaResponseModel>
            ) {
                handleImageResponse(response)
            }
            override fun onFailure(call: Call<NasaResponseModel>, t: Throwable) {
                liveDataForViewToObserve.value = DailyImage.Error(t)
            }
        }
        retrofitImpl.getNasaDayPictureService().getImage(apiKey).enqueue(callback)
    }

    private fun handleImageResponse(response: Response<NasaResponseModel>) {
        if (response.isSuccessful && response.body() != null) {
            liveDataForViewToObserve.value = DailyImage.Success(response.body()!!)
            return
        }

        val message = response.message()
        if (message.isNullOrEmpty()) {
            liveDataForViewToObserve.value = DailyImage.Error(Throwable("Unidentified error"))
        } else {
            liveDataForViewToObserve.value = DailyImage.Error(Throwable(message))
        }
    }
}