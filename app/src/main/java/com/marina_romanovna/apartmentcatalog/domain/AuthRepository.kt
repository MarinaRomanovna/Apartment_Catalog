package com.marina_romanovna.apartmentcatalog.domain

import com.marina_romanovna.apartmentcatalog.data.responses.LoginResponse
import io.reactivex.rxjava3.core.Single

interface AuthRepository {

    fun login(email: String, password: String): Single<LoginResponse>

}