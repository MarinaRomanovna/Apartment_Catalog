package com.marina_romanovna.apartmentcatalog.di

import android.app.Application
import com.marina_romanovna.apartmentcatalog.data.database.ApartmentDatabase
import com.marina_romanovna.apartmentcatalog.data.database.ApartmentListDao
import com.marina_romanovna.apartmentcatalog.data.repositories.ApartmentRepositoryImpl
import com.marina_romanovna.apartmentcatalog.data.repositories.AuthRepositoryImpl
import com.marina_romanovna.apartmentcatalog.domain.UserPreferences
import com.marina_romanovna.apartmentcatalog.data.userpreferences.UserPreferencesImpl
import com.marina_romanovna.apartmentcatalog.domain.ApartmentRepository
import com.marina_romanovna.apartmentcatalog.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindApartmentRepository(apartmentRepositoryImpl: ApartmentRepositoryImpl): ApartmentRepository

    @Binds
    @ApplicationScope
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideApartmentListDao(apartmentDatabase: ApartmentDatabase): ApartmentListDao {
            return apartmentDatabase.apartmentListDao()
        }

        @Provides
        @ApplicationScope
        fun provideUserPreferences(application: Application): UserPreferences {
            return UserPreferencesImpl(application)
        }
    }
}