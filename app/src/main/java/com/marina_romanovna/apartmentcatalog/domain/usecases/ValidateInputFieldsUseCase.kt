package com.marina_romanovna.apartmentcatalog.domain.usecases

import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ValidateInputFieldsUseCase @Inject constructor() {

    companion object {
        const val DEFAULT_STRING = ""
        const val DEFAULT_INT = 0
        const val DEFAULT_DOUBLE = 0.0
    }

    fun execute(apartmentItem: ApartmentItem): Single<Boolean> {
        return Single.fromCallable {
            !(apartmentItem.address.isBlank()
                    || apartmentItem.price == DEFAULT_INT
                    || apartmentItem.square == DEFAULT_DOUBLE
                    || apartmentItem.numberOfRooms == DEFAULT_INT
                    || apartmentItem.floor == DEFAULT_INT)
        }
    }
}