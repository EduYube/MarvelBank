package com.eyubero.marvelbank.data

data class ResponseModel<out T>(
    val code: Int,
    val status: String,
    val data: T
)
