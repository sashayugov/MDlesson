package com.example.mdlesson.ui.dailyImageUi

import com.example.mdlesson.retrofit.model.NasaResponseModel

sealed class DailyImage {

    data class Success(val serverResponseData: NasaResponseModel) : DailyImage()

    data class Error(val error: Throwable) : DailyImage()

    data class Loading(val progress: Int?) : DailyImage()
}
