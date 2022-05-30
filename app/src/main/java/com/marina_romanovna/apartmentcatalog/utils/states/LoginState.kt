package com.marina_romanovna.apartmentcatalog.utils.states

import okhttp3.ResponseBody

sealed class LoginState<out T> {
    data class Success<out T>(val value: T) : LoginState<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : LoginState<Nothing>()
}