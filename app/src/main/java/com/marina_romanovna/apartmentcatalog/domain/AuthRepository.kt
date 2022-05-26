package com.marina_romanovna.apartmentcatalog.domain

import io.reactivex.rxjava3.core.Completable

interface AuthRepository {

    fun login(email: String, password: String): Completable

}