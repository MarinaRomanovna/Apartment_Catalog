package com.marina_romanovna.apartmentcatalog.domain

import kotlinx.coroutines.flow.Flow

interface UserPreferences {

    val authToken: Flow<String?>

    suspend fun saveAuthToken(authToken: String)
}