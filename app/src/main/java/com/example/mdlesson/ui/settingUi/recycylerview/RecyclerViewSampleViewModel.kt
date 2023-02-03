package com.example.mdlesson.ui.settingUi.recycylerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mdlesson.ui.settingUi.recycylerview.model.AdvertisingUiModel
import com.example.mdlesson.ui.settingUi.recycylerview.model.PlanetUiModel
import com.example.mdlesson.ui.settingUi.recycylerview.model.SampleListItem
import java.util.*

private const val MARS_PICTURE_URL = "https://findicons.com/files/icons/475/solar_system/256/mars.png"
private const val EARTH_PICTURE_URL = "https://findicons.com/files/icons/144/web/256/earth.png"

class RecyclerViewSampleViewModel : ViewModel() {

    private val itemsLiveData = MutableLiveData<List<SampleListItem>>(emptyList())
    private val messagesLiveData = MutableLiveData<String>()

    fun getItems(): LiveData<List<SampleListItem>> {
        return itemsLiveData
    }

    fun getMessage(): LiveData<String> {
        return messagesLiveData
    }

    fun loadData() {
        val planet1 = PlanetUiModel(
            id = UUID.randomUUID().toString(),
            pictureUrl = EARTH_PICTURE_URL,
            name = "Earth",
        )

        val planet2 = planet1.copy(id = UUID.randomUUID().toString())
        val planet3 = planet1.copy(id = UUID.randomUUID().toString())
        val planet4 = PlanetUiModel(
            id = UUID.randomUUID().toString(),
            pictureUrl = MARS_PICTURE_URL,
            name = "Mars",
        )
        val planet5 = planet4.copy(id = UUID.randomUUID().toString())
        val planet6 = planet4.copy(id = UUID.randomUUID().toString())
        val planet7 = planet1.copy(id = UUID.randomUUID().toString())
        val planet8 = planet1.copy(id = UUID.randomUUID().toString())
        val planet9 = planet1.copy(id = UUID.randomUUID().toString())
        val planet10 = planet1.copy(id = UUID.randomUUID().toString())

        val advertisingUiModel1 = AdvertisingUiModel(
            id = UUID.randomUUID().toString(),
            title = "Реклама ботинок",
            description = "Покупайте ботинки, удобные"
        )

        val advertisingUiModel2 = AdvertisingUiModel(
            id = UUID.randomUUID().toString(),
            title = "Реклама жвачки",
            description = "Жуйте ботинки, вкусные"
        )

        val items = listOf(
            planet1,
            planet2,
            planet3,
            advertisingUiModel1,
            planet4,
            planet5,
            planet6,
            planet7,
            advertisingUiModel2,
            planet8,
            planet9,
            planet10,
        )

        itemsLiveData.value = items
    }

    fun onPlanetClick(uiModel: PlanetUiModel) {
        messagesLiveData.value = uiModel.name
    }

    fun onAdvertisingClick(uiModel: AdvertisingUiModel) {
        messagesLiveData.value = uiModel.description
    }

    fun onItemMoved(from: Int, to: Int) {
        val newMutableList = requireCurrentList().toMutableList()
        Collections.swap(newMutableList, from, to)

        itemsLiveData.value = newMutableList
    }

    fun onItemRemoved(position: Int) {
        val newMutableList = requireCurrentList().toMutableList()
        newMutableList.removeAt(position)

        itemsLiveData.value = newMutableList
    }

    private fun requireCurrentList(): List<SampleListItem> {
        return itemsLiveData.value ?: throw java.lang.IllegalStateException("Item list is null")
    }
}