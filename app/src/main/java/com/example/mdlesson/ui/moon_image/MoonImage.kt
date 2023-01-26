package com.example.mdlesson.ui.moon_image

import com.example.mdlesson.retrofit.NasaResponseMoonModel

sealed class MoonImage {

    data class Success(val serverResponseData: NasaResponseMoonModel) : MoonImage()

    data class Error(val error: Throwable) : MoonImage()

    data class Loading(val progress: Int?) : MoonImage()

}