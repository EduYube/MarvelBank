package com.eyubero.marvelbank.repository

import com.eyubero.marvelbank.BuildConfig
import com.eyubero.marvelbank.di.PrivateKey
import com.eyubero.marvelbank.di.PublicKey
import com.eyubero.marvelbank.domain.DomainResponse
import com.eyubero.marvelbank.domain.HeroErrorResponse
import com.eyubero.marvelbank.domain.HeroesList
import com.eyubero.marvelbank.mapper.HeroesListMapper
import com.eyubero.marvelbank.service.CallService
import com.eyubero.marvelbank.utils.Encryptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class HeroesListRepository @Inject constructor(
    private val service: CallService,
    private val encryptor: Encryptor,
    private val heroesListMapper: HeroesListMapper,
    @PublicKey private val publicKey: String,
    @PrivateKey private val privateKey: String,
) {
    suspend fun getHeroesList(
        offset: Int?,
        nameStartsWith: String?,
    ): DomainResponse<HeroesList, HeroErrorResponse> = withContext(Dispatchers.IO) {
        val timestamp = System.currentTimeMillis()
        val md5 = encryptor.buildMD5Digest("$timestamp$privateKey$publicKey")
        try {
            val response = service.getHeroesList(timestamp, md5, offset, nameStartsWith, BuildConfig.PUBLIC_KEY)
            if( response.isSuccessful && response.body() != null) {
                val heroesList: HeroesList = heroesListMapper.toDomainModel(response.body()!!)
                DomainResponse.Success(heroesList)
            } else {
                DomainResponse.Error(heroesListMapper.toErrorDomainModel(response.message()))
            }
        } catch (e: IOException) {
            DomainResponse.Error(heroesListMapper.toErrorDomainModel(e))
        }
    }

}