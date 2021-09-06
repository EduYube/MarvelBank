package com.eyubero.marvelbank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eyubero.marvelbank.domain.DomainResponse
import com.eyubero.marvelbank.usecase.GetHeroDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    val getHeroDetailUseCase: GetHeroDetailUseCase
): ViewModel(){
    var selectedCharacterId = MutableStateFlow(0)
    private val _detailStateFlow = MutableStateFlow<HeroDetailViewModelState>(HeroDetailViewModelState.Loading)
    val state: StateFlow<HeroDetailViewModelState>
        get() = _detailStateFlow

    init {
        viewModelScope.launch {
            selectedCharacterId.collect {
                if (it != 0) getDetailCharacter(it)
            }
        }
    }

    private suspend fun getDetailCharacter(id: Int){
        getHeroDetailUseCase
        val params = GetHeroDetailUseCase.Params(characterId = id)
        when(val result = getHeroDetailUseCase(params)){
            is DomainResponse.Success -> {
                if(result.model.results.isNotEmpty()){
                    _detailStateFlow.value = (HeroDetailViewModelState.Success(result.model.results[0]))
                }else{
                    _detailStateFlow.value = (HeroDetailViewModelState.Empty)
                }
            }
            is DomainResponse.Error -> {
                _detailStateFlow.value = (HeroDetailViewModelState.Error)
            }
            else  ->  _detailStateFlow.value = (HeroDetailViewModelState.Loading)
        }
    }
}