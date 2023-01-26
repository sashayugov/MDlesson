package com.example.mdlesson.ui.mars_image

import com.example.mdlesson.retrofit.NasaResponseMarsModel

sealed class MarsImage {

    data class Success(val serverResponseData: NasaResponseMarsModel) : MarsImage()

    data class Error(val error: Throwable) : MarsImage()

    data class Loading(val progress: Int?) : MarsImage()


}