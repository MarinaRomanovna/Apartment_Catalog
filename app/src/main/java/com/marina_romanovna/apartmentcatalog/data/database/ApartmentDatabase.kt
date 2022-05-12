package com.marina_romanovna.apartmentcatalog.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ApartmentItemEntity::class
    ], version = 1, exportSchema = false
)
abstract class ApartmentDatabase : RoomDatabase() {

    abstract fun apartmentListDao(): ApartmentListDao
}