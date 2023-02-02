package com.example.mdlesson.retrofit.model

import com.google.gson.annotations.SerializedName

data class NasaResponseMarsModel(
    @SerializedName("photos")
    val photos: Array<ResponsePhotos?>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NasaResponseMarsModel

        if (!photos.contentEquals(other.photos)) return false

        return true
    }

    override fun hashCode(): Int {
        return photos.contentHashCode()
    }
}

data class ResponsePhotos(
    @SerializedName("img_src")
    val imgSrc: String?
)
