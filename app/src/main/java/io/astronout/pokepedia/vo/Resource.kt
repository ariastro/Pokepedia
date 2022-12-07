package io.astronout.pokepedia.vo

sealed class Resource<out T: Any> {
    data class Success<out T: Any>(val data: T): Resource<T>()
    data class Error(val message: String): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}