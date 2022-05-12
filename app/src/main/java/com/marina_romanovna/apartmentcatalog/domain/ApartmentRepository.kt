package com.marina_romanovna.apartmentcatalog.domain

import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ApartmentRepository {

    fun addApartmentItem(apartmentItem: ApartmentItem): Completable

    fun deleteApartmentItem(apartmentItem: ApartmentItem): Completable

    fun editApartmentItem(apartmentItem: ApartmentItem): Completable

    fun getApartmentItem(apartmentId: Int): Single<ApartmentItem>

    fun getApartmentList(): Single<List<ApartmentItem>>
}