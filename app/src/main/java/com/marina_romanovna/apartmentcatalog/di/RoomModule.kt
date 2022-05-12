package com.marina_romanovna.apartmentcatalog.di

import android.app.Application
import androidx.room.Room
import com.marina_romanovna.apartmentcatalog.data.database.ApartmentDatabase
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    @ApplicationScope
    fun provideDatabase(application: Application) =
        Room.databaseBuilder(
            application,
            ApartmentDatabase::class.java,
            DATABASE_NAME
        ).build()

    companion object {
        private const val DATABASE_NAME = "apartment_room_database"
    }
}