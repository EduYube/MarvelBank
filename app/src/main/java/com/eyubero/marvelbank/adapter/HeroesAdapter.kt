package com.eyubero.marvelbank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eyubero.marvelbank.domain.Hero

class HeroesAdapter internal constructor(private val heroesList: List<Hero>,
                                         private val mListener: (Hero) -> Unit) :
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
        holder.bindView(hero = heroesList[position], holder.itemView.context, mListener)
    }

    override fun getItemCount(): Int = heroesList.size

}