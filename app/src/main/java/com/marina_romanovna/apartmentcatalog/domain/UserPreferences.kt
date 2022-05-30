package com.marina_romanovna.apartmentcatalog.domain

import kotlinx.coroutines.flow.Flow

abstract class UserPreferences {

    abstract val authToken: Flow<String?>

    abstract suspend fun saveAuthToken(authToken: String)
}