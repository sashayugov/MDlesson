package com.example.mdlesson.ui.earth_image

import com.example.mdlesson.retrofit.NasaResponseEarthModel

sealed class EarthImage {

    data class Success(val serverResponseData: NasaResponseEarthModel) : EarthImage()

    data class Error(val error: Throwable) : EarthImage()

    data class Loading(val progress: Int?) : EarthImage()
}
