package com.marina_romanovna.apartmentcatalog.domain.usecases

import com.marina_romanovna.apartmentcatalog.domain.ApartmentRepository
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AddApartmentItemUseCase @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) {
    fun execute(apartmentItem: ApartmentItem): Completable {
        return apartmentRepository.addApartmentItem(apartmentItem)
    }
}