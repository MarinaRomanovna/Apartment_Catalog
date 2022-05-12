package com.marina_romanovna.apartmentcatalog.data

import com.marina_romanovna.apartmentcatalog.data.database.ApartmentListDao
import com.marina_romanovna.apartmentcatalog.data.mappers.ApartmentMapper
import com.marina_romanovna.apartmentcatalog.domain.ApartmentRepository
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ApartmentRepositoryImpl @Inject constructor(
    private val apartmentListDao: ApartmentListDao,
    private val mapper: ApartmentMapper
) : ApartmentRepository {

    override fun addApartmentItem(apartmentItem: ApartmentItem): Completable {
        return apartmentListDao.addApartmentItem(mapper.mapModelToEntity(apartmentItem))
    }

    override fun deleteApartmentItem(apartmentItem: ApartmentItem): Completable {
        return apartmentListDao.deleteApartmentItem(apartmentItem.id)
    }

    override fun editApartmentItem(apartmentItem: ApartmentItem): Completable {
        return apartmentListDao.addApartmentItem(mapper.mapModelToEntity(apartmentItem))
    }

    override fun getApartmentItem(apartmentId: Int): Single<ApartmentItem> {
        return apartmentListDao.getApartmentItem(apartmentId).map {
            mapper.mapEntityToModel(it)
        }
    }

    override fun getApartmentList(): Single<List<ApartmentItem>> {
        return apartmentListDao.getApartmentList().map {
            mapper.mapListEntityToListModel(it)
        }
    }
}