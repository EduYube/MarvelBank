package com.eyubero.marvelbank.viewmodel

import com.eyubero.marvelbank.domain.Hero


sealed class HeroDetailViewModelState {
    object Empty : HeroDetailViewModelState()
    data class Success(val hero: Hero): HeroDetailViewModelState()
    object Loading: HeroDetailViewModelState()
    object Error : HeroDetailViewModelState()
}
