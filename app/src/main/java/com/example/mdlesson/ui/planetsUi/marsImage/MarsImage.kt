package com.example.mdlesson.ui.planetsUi.marsImage

import com.example.mdlesson.retrofit.model.NasaResponseMarsModel

sealed class MarsImage {

    data class Success(val serverResponseData: NasaResponseMarsModel) : MarsImage()

    data class Error(val error: Throwable) : MarsImage()

    data class Loading(val progress: Int?) : MarsImage()


}