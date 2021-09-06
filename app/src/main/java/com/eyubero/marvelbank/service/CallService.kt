package com.eyubero.marvelbank.service

import com.eyubero.marvelbank.data.HeroesListModel
import com.eyubero.marvelbank.data.ResponseModel
import com.eyubero.marvelbank.di.PublicKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CallService {

    @GET ("characters")
    suspend fun getHeroesList(
        @Query("ts") ts: Long,
        @Query("hash") md5Digest: String,
        @Query("offset")  offset: Int?,
        @Query("nameStartsWith") nameStartsWith: String?,
        @Query("apikey") @PublicKey publicKey: String
    ): Response<ResponseModel<HeroesListModel>>


    @GET("characters/{characterId}")
    suspend fun getCharacterDetail(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: Long,
        @Query("hash") md5Digest: String,
        @Query("apikey") @PublicKey publicKey: String
        ): Response<ResponseModel<HeroesListModel>>
}