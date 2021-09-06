package com.eyubero.marvelbank.domain

import java.io.Serializable

data class HeroesList (
    val count: Int,
    val offset: Int,
    val results: List<Hero>,
)

class Hero(
    val id: Int,
    val name: String,
    val description: String?,
    val image: HeroImage
) : Serializable

data class HeroImage(
    val path: String,
    val extension: String
)
