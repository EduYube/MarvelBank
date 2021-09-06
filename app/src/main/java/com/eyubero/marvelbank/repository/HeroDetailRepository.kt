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

class HeroDetailRepository @Inject constructor(
    private val service: CallService,
    private val mapper: HeroesListMapper,
    private val encryptor: Encryptor,
    @PublicKey private val publicKey: String,
    @PrivateKey private val privateKey: String,
){
    suspend fun  getDetail(characterId: Int
    ): DomainResponse<HeroesList, HeroErrorResponse> = withContext(Dispatchers.IO) {
        val timestamp = System.currentTimeMillis()
        val has =  encryptor.buildMD5Digest("$timestamp$privateKey$publicKey")
        try {
            val response = service.getCharacterDetail(characterId,timestamp, has, BuildConfig.PUBLIC_KEY)
            if (response.isSuccessful && response.body()!= null) {
                val domainList: HeroesList =  mapper.toDomainModel(response.body()!!)
                DomainResponse.Success(domainList)
            }else{
                DomainResponse.Error(mapper.toErrorDomainModel(response.message()))
            }
        }catch (e: IOException){
            DomainResponse.Error(mapper.toErrorDomainModel(e))
        }
    }
}
