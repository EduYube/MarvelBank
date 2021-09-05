package com.eyubero.marvelbank.viewmodel

import com.eyubero.marvelbank.domain.HeroesList


sealed class HeroesListViewModelState {
    object Empty : HeroesListViewModelState()
    data class Success(val heroesList: HeroesList): HeroesListViewModelState()
    object Loading: HeroesListViewModelState()
    object Error : HeroesListViewModelState()
}