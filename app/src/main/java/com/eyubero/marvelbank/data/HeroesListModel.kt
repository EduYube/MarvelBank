package com.eyubero.marvelbank.data

import com.google.gson.annotations.SerializedName

data class HeroesListModel(
    val offset: Int,
    val count: Int,
    @SerializedName("results")
    val results: List<HeroModel>
)

data class HeroModel(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("thumbnail")
    val image: HeroImageModel
)

data class HeroImageModel(
    val path: String,
    val extension: String
)
