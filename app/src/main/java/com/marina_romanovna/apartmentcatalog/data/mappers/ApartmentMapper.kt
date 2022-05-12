package com.marina_romanovna.apartmentcatalog.data.mappers

import com.marina_romanovna.apartmentcatalog.data.database.ApartmentItemEntity
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import javax.inject.Inject

class ApartmentMapper @Inject constructor() {

    fun mapModelToEntity(apartmentItem: ApartmentItem): ApartmentItemEntity {
        return ApartmentItemEntity(
            id = apartmentItem.id,
            photo = apartmentItem.photo,
            price = apartmentItem.price,
            square = apartmentItem.square,
            address = apartmentItem.address,
            numberOfRooms = apartmentItem.numberOfRooms,
            pricePerSquareMeter = apartmentItem.pricePerSquareMeter,
            floor = apartmentItem.floor
        )
    }

    fun mapEntityToModel(apartmentItemEntity: ApartmentItemEntity): ApartmentItem {
        return ApartmentItem(
            id = apartmentItemEntity.id,
            photo = apartmentItemEntity.photo,
            price = apartmentItemEntity.price,
            square = apartmentItemEntity.square,
            address = apartmentItemEntity.address,
            numberOfRooms = apartmentItemEntity.numberOfRooms,
            pricePerSquareMeter = apartmentItemEntity.pricePerSquareMeter,
            floor = apartmentItemEntity.floor
        )
    }

    fun mapListEntityToListModel(list: List<ApartmentItemEntity>): List<ApartmentItem> {
        return list.map {
            mapEntityToModel(it)
        }
    }
}