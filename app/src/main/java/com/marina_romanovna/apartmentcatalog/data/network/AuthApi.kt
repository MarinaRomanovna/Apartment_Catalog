package com.marina_romanovna.apartmentcatalog.data.network

import com.marina_romanovna.apartmentcatalog.data.responses.LoginResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<LoginResponse>
}