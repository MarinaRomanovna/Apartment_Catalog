package com.marina_romanovna.apartmentcatalog.data.repositories

import com.marina_romanovna.apartmentcatalog.data.network.AuthApi
import com.marina_romanovna.apartmentcatalog.data.responses.LoginResponse
import com.marina_romanovna.apartmentcatalog.domain.AuthRepository
import com.marina_romanovna.apartmentcatalog.domain.UserPreferences
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val preferences: UserPreferences
) : AuthRepository {

    override fun login(email: String, password: String): Single<LoginResponse> {
        return authApi.login(email, password)
    }

    override suspend fun saveAuthToken(authToken: String) {
        preferences.saveAuthToken(authToken)
    }
}