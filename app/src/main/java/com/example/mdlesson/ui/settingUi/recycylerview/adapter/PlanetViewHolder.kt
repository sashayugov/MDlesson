package com.example.mdlesson.ui.settingUi.recycylerview.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.mdlesson.R
import com.example.mdlesson.ui.settingUi.recycylerview.model.PlanetUiModel

class PlanetViewHolder(
    view: View, private val onPlanetClickListener: (item: PlanetUiModel) -> Unit,
) : RecyclerView.ViewHolder(view) {

    private var planetImageView: ImageView = view.findViewById(R.id.image_view_planet)
    private var planetNameTextView: TextView = view.findViewById(R.id.text_view_planet)

    fun bind(planetUiModel: PlanetUiModel) {
        planetImageView.load(planetUiModel.pictureUrl) {
            error(android.R.drawable.ic_menu_report_image)
            placeholder(android.R.drawable.ic_menu_gallery)
        }
        planetNameTextView.text = planetUiModel.name
        itemView.setOnClickListener { onPlanetClickListener(planetUiModel) }
    }
}