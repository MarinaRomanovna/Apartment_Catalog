package com.marina_romanovna.apartmentcatalog.domain.usecases

import com.marina_romanovna.apartmentcatalog.domain.AuthRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    fun execute(email: String, password: String): Completable {
        return authRepository.login(email, password)
    }

}