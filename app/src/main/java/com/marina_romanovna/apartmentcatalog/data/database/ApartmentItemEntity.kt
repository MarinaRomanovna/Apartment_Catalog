package com.marina_romanovna.apartmentcatalog.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marina_romanovna.apartmentcatalog.data.database.ApartmentItemEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ApartmentItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val photo: String,
    val price: Int,
    val square: Double,
    val address: String,
    val numberOfRooms: Int,
    val pricePerSquareMeter: Double,
    val floor: Int
) {
    companion object {
        const val TABLE_NAME = "apartment_list_item_entities"
    }
}