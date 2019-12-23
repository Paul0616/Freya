package com.softtehnica.freya.models

data class ApiResponseModel(
    val payload: Any,
    val status: Int,
    val message: String,
    val isSuccess: Boolean,
    val errorMessage: String
)