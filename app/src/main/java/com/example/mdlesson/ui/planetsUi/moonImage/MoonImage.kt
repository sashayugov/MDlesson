package com.example.mdlesson.ui.planetsUi.moonImage

import com.example.mdlesson.retrofit.model.NasaResponseMoonModel

sealed class MoonImage {

    data class Success(val serverResponseData: NasaResponseMoonModel) : MoonImage()

    data class Error(val error: Throwable) : MoonImage()

    data class Loading(val progress: Int?) : MoonImage()

}