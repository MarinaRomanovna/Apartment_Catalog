package com.marina_romanovna.apartmentcatalog.domain.usecases

import com.marina_romanovna.apartmentcatalog.domain.ApartmentRepository
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetApartmentItemUseCase @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) {
    fun execute(apartmentId: Int): Single<ApartmentItem> {
        return apartmentRepository.getApartmentItem(apartmentId)
    }
}