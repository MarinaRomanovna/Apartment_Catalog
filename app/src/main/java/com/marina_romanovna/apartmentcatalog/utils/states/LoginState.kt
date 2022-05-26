package com.marina_romanovna.apartmentcatalog.utils.states

sealed class LoginState {
    object Success : LoginState()
    object Loading : LoginState()
    object Error : LoginState()
}