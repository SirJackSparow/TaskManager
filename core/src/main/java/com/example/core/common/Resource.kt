package com.example.core.common

sealed class Resource<out D> {
    data class Success<D>(val data: D) : Resource<D>()
    data class Error<D>(val message: String, val data: D? = null) : Resource<D>()
    data object Loading : Resource<Nothing>()
}
