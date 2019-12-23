package com.softtehnica.freya.models

data class ErrorModel(
    val status: Int,
    val message: String,
    val isSuccess: Boolean,
    val errorMessage: String
)