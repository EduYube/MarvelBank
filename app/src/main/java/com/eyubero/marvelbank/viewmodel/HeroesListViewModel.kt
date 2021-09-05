package com.eyubero.marvelbank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eyubero.marvelbank.domain.DomainResponse
import com.eyubero.marvelbank.domain.Hero
import com.eyubero.marvelbank.usecase.GetHeroesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroesListViewModel @Inject constructor(
    private val getHeroesListUseCase: GetHeroesListUseCase
) : ViewModel() {

    val moreHeroes = MutableStateFlow(0)
    private val heroesList = ArrayList<Hero>()
    private var count = 0
    private val _listLiveData =
        MutableStateFlow<HeroesListViewModelState>(HeroesListViewModelState.Loading)
    val state: StateFlow<HeroesListViewModelState>
        get() = _listLiveData

    init {
        viewModelScope.launch {
            moreHeroes.collect {
            if ( it >= (count -4)) {
                getHeroesList()
            }
        }
        }
    }

    private suspend fun getHeroesList() {
        val args = GetHeroesListUseCase.Args(offset = count, nameStartsWith = null)
        when (val data = getHeroesListUseCase(args)) {
            is DomainResponse.Success -> {
                if (data.model.results.isNotEmpty()) {
                    heroesList.addAll(heroesList.lastIndex + 1, data.model.results)
                    _listLiveData.value = (HeroesListViewModelState.Success(data.model))
                    count = data.model.count + data.model.offset
                } else {
                    _listLiveData.value = (HeroesListViewModelState.Empty)
                }
            }
            is DomainResponse.Error -> {
                _listLiveData.value = (HeroesListViewModelState.Error)
            }
            else -> {
                _listLiveData.value = (HeroesListViewModelState.Loading)
            }
        }
    }
}
