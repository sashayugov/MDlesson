package com.example.mdlesson.ui.settingUi.recycylerview.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mdlesson.R
import com.example.mdlesson.ui.settingUi.recycylerview.model.AdvertisingUiModel

class AdvertisingViewHolder(
    view: View,
    private val onAdvertisingClickListener: (item: AdvertisingUiModel) -> Unit
) : RecyclerView.ViewHolder(view) {

    private var advertisingTitleTextView: TextView = view.findViewById(R.id.title_advertising_view)
    private var advertisingDescriptionTextView: TextView =
        view.findViewById(R.id.description_advertising_view)

    fun bind(advertisingUiModel: AdvertisingUiModel) {
        advertisingTitleTextView.text = advertisingUiModel.title
        advertisingDescriptionTextView.text = advertisingUiModel.description

        itemView.setOnClickListener { onAdvertisingClickListener(advertisingUiModel) }
    }
}