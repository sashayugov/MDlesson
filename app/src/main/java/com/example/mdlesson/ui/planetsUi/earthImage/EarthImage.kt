package com.example.mdlesson.ui.planetsUi.earthImage

import com.example.mdlesson.retrofit.model.NasaResponseEarthModel

sealed class EarthImage {

    data class Success(val serverResponseData: NasaResponseEarthModel) : EarthImage()

    data class Error(val error: Throwable) : EarthImage()

    data class Loading(val progress: Int?) : EarthImage()
}
