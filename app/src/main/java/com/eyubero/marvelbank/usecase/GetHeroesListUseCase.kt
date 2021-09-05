package com.eyubero.marvelbank.usecase

import com.eyubero.marvelbank.domain.DomainResponse
import com.eyubero.marvelbank.domain.HeroErrorResponse
import com.eyubero.marvelbank.domain.HeroesList
import com.eyubero.marvelbank.repository.HeroesListRepository
import javax.inject.Inject

class GetHeroesListUseCase @Inject constructor(
    private val heroesListRepository: HeroesListRepository
) {

    suspend operator fun invoke (args: Args): DomainResponse<HeroesList, HeroErrorResponse> {
        return heroesListRepository.getHeroesList(args.offset, args.nameStartsWith)
    }

    data class Args(
        val offset: Int?,
        val nameStartsWith: String?
    )
}