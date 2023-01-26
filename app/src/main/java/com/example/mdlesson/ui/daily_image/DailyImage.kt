package com.example.mdlesson.ui.daily_image

import com.example.mdlesson.retrofit.NasaResponseModel

sealed class DailyImage {

    data class Success(val serverResponseData: NasaResponseModel) : DailyImage()

    data class Error(val error: Throwable) : DailyImage()

    data class Loading(val progress: Int?) : DailyImage()
}
