package com.marina_romanovna.apartmentcatalog.data.repositories

import com.marina_romanovna.apartmentcatalog.data.network.AuthApi
import com.marina_romanovna.apartmentcatalog.domain.AuthRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override fun login(email: String, password: String): Completable {
        return authApi.login(email, password)
    }
}