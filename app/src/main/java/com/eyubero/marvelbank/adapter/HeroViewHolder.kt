package com.eyubero.marvelbank.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eyubero.marvelbank.R
import com.eyubero.marvelbank.domain.Hero
import java.net.URL


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
    private val heroImage: ImageView = itemView.findViewById(R.id.hero_image)
    private val heroClick = itemView.findViewById<ConstraintLayout>(R.id.item_layout)

    fun bindView(
        hero: Hero,
        context: Context,
        mListener1: (Hero) -> Unit
    ) {
        heroName.text = hero.name
        val url = URL(hero.image.path+"."+hero.image.extension)
        Glide.with(context).load(url.toString()).into(heroImage)
        heroClick.setOnClickListener {
            mListener1 (hero)
        }

    }

}
