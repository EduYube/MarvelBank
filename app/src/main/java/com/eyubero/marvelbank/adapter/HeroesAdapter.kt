package com.eyubero.marvelbank.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HeroesAdapter internal constructor(private val heroesList: List<String>) :
    RecyclerView.Adapter<HeroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(
            LayoutInflater.from(
                parent.context
            ),
            parent
        )
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bindView(hero = heroesList[position], null)
    }

    override fun getItemCount(): Int = heroesList.size

}