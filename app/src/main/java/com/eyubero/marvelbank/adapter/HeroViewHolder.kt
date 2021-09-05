package com.eyubero.marvelbank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eyubero.marvelbank.R

class HeroViewHolder internal constructor(
    inflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.hero_item,
        parent,
        false
    )
) {

    private val heroName = itemView.findViewById<TextView>(R.id.hero_name)

    fun bindView(hero: String, mListener: ((String) -> Unit)?) {
        heroName.text = hero
    }

}
