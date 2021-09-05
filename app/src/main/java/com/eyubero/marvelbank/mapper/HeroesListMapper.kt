package com.eyubero.marvelbank.mapper

import com.eyubero.marvelbank.data.HeroesListModel
import com.eyubero.marvelbank.data.ResponseModel
import com.eyubero.marvelbank.domain.*
import java.io.IOException

class HeroesListMapper {

    fun toDomainModel(data: ResponseModel<HeroesListModel>): HeroesList {
        return HeroesList(
            count = data.data.count,
            offset = data.data.offset,
            results = data.data.results.map {
                Hero(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    image = HeroImage(
                        it.image.path,
                        it.image.extension
                    )
                )
            }
        )
    }


    fun toErrorDomainModel(message: String): HeroErrorResponse {
        return HeroErrorResponse(ErrorCode.Unknown, message)
    }

    fun toErrorDomainModel(exception: Exception): HeroErrorResponse {
        val errorCode = when (exception) {
            is IOException -> ErrorCode.ServerNotReachable
            else -> ErrorCode.Unknown
        }
        return HeroErrorResponse(errorCode, exception.message)
    }
}

