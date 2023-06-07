package com.instance.dataxbranch.domain


sealed class Response<out T> {


    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T
    ): Response<T>()

    data class Error(
        val message: String
    ): Response<Nothing>()
}
fun <T> unpackResponse(response: Response<T>, onSuccess: (T) -> Unit, onError: (String) -> Unit) {
    when (response) {
        is Response.Success -> onSuccess(response.data)
        is Response.Error -> onError(response.message)
        is Response.Loading -> {} // Optionally handle the loading state
    }
}
fun <T> unpackResponseWithReturn(response: Response<T>, onSuccess: (T) -> Unit, onError: (String) -> Unit): T {
    return when (response) {
        is Response.Success -> {
            onSuccess(response.data)
            response.data
        }
        is Response.Error -> {
            onError(response.message)
            throw Exception(response.message)
        }
        is Response.Loading -> throw IllegalStateException("Loading state should not be unpacked")
    }
}