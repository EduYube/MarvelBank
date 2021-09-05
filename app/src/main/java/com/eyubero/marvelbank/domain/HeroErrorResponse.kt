package com.eyubero.marvelbank.domain

data class HeroErrorResponse(
    val errorCode: ErrorCode,
    val message: String? = null
)

sealed class ErrorCode {
    object ServerNotReachable : ErrorCode()
    object Unknown : ErrorCode()
}