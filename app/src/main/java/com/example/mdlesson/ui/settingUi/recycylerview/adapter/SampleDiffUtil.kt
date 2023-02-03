package com.example.mdlesson.ui.settingUi.recycylerview.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mdlesson.ui.settingUi.recycylerview.model.AdvertisingUiModel
import com.example.mdlesson.ui.settingUi.recycylerview.model.PlanetUiModel
import com.example.mdlesson.ui.settingUi.recycylerview.model.SampleListItem

class SampleDiffUtil(
    private val oldList: List<SampleListItem>,
    private val newList: List<SampleListItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when (oldItem) {
            is PlanetUiModel -> newItem is PlanetUiModel && oldItem.id == newItem.id
            is AdvertisingUiModel -> newItem is AdvertisingUiModel && oldItem.id == newItem.id
            else -> throw IllegalArgumentException("unknown item type")
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when (oldItem) {
            is PlanetUiModel -> newItem is PlanetUiModel && oldItem == newItem
            is AdvertisingUiModel -> newItem is AdvertisingUiModel && oldItem == newItem
            else -> throw IllegalArgumentException("unknown item type")
        }
    }
}