package com.example.mdlesson.ui.settingUi.recycylerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mdlesson.ui.settingUi.recycylerview.model.SampleListItem

private const val MARS_PICTURE_URL = "https://findicons.com/files/icons/475/solar_system/256/mars.png"
private const val EARTH_PICTURE_URL = "https://findicons.com/files/icons/144/web/256/earth.png"

class RecyclerViewSampleViewModel : ViewModel() {

    private val itemsLiveData = MutableLiveData<List<SampleListItem>>(emptyList())
    private val messagesLiveData = MutableLiveData<String>()

}