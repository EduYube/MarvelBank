package com.eyubero.marvelbank.usecase

import com.eyubero.marvelbank.domain.DomainResponse
import com.eyubero.marvelbank.domain.HeroErrorResponse
import com.eyubero.marvelbank.domain.HeroesList
import com.eyubero.marvelbank.repository.HeroDetailRepository
import javax.inject.Inject

class GetHeroDetailUseCase @Inject constructor(
    private val heroDetailRepository: HeroDetailRepository
){
    data class Params(
        val characterId: Int
    )

    suspend operator fun invoke(params: Params): DomainResponse<HeroesList, HeroErrorResponse> {
        return heroDetailRepository.getDetail(params.characterId)
    }
}
