package com.marina_romanovna.apartmentcatalog.domain.usecases

import com.marina_romanovna.apartmentcatalog.domain.AuthRepository
import com.marina_romanovna.apartmentcatalog.data.responses.LoginResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    fun execute(email: String, password: String): Single<LoginResponse> {
        return authRepository.login(email, password)
    }

}