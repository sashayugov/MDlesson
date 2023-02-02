package com.example.mdlesson.ui.settingUi.recycylerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mdlesson.R
import com.example.mdlesson.ui.settingUi.recycylerview.model.AdvertisingUiModel
import com.example.mdlesson.ui.settingUi.recycylerview.model.PlanetUiModel
import com.example.mdlesson.ui.settingUi.recycylerview.model.SampleListItem

private const val VIEW_TYPE_PLANET = 0
private const val VIEW_TYPE_ADVERTISING = 1

class SampleAdapter(
    private val onPlanetClickListener: ((item: PlanetUiModel) -> Unit),
    private val onAdvertisingClickListener: ((item: AdvertisingUiModel) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = emptyList<SampleListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_PLANET -> {
                PlanetViewHolder(
                    view = inflater.inflate(R.layout.item_planet_view, parent, false) as View,
                    onPlanetClickListener = onPlanetClickListener
                )
            }
            VIEW_TYPE_ADVERTISING -> {
                AdvertisingViewHolder(
                    view = inflater.inflate(R.layout.item_advertising_view, parent, false) as View,
                    onAdvertisingClickListener = onAdvertisingClickListener
                )
            }
            else -> {
                throw IllegalArgumentException("Unknown type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_PLANET) {
            holder as PlanetViewHolder
            val planetUiModel = items[position] as PlanetUiModel
            holder.bind(planetUiModel)
        } else {
            holder as AdvertisingViewHolder
            val advertisingUiModel = items[position] as AdvertisingUiModel
            holder.bind(advertisingUiModel)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PlanetUiModel -> VIEW_TYPE_PLANET
            is AdvertisingUiModel -> VIEW_TYPE_ADVERTISING
            else -> throw IllegalArgumentException("Unknown type")
        }
    }
}