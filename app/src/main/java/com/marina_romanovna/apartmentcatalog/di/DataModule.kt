package com.marina_romanovna.apartmentcatalog.di

import com.marina_romanovna.apartmentcatalog.data.ApartmentRepositoryImpl
import com.marina_romanovna.apartmentcatalog.data.database.ApartmentDatabase
import com.marina_romanovna.apartmentcatalog.data.database.ApartmentListDao
import com.marina_romanovna.apartmentcatalog.domain.ApartmentRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindApartmentRepository(apartmentRepositoryImpl: ApartmentRepositoryImpl): ApartmentRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideApartmentListDao(apartmentDatabase: ApartmentDatabase): ApartmentListDao {
            return apartmentDatabase.apartmentListDao()
        }
    }
}