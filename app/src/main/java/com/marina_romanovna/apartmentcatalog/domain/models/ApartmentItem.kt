package com.marina_romanovna.apartmentcatalog.domain.models

data class ApartmentItem(
    val photo: String,
    val price: Int,
    val square: Double,
    val address: String,
    val numberOfRooms: Int,
    val pricePerSquareMeter: Double,
    val floor: Int,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}

