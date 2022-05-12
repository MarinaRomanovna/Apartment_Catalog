package com.marina_romanovna.apartmentcatalog.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ApartmentListDao {

    @Query("SELECT * FROM apartment_list_item_entities")
    fun getApartmentList(): Single<List<ApartmentItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addApartmentItem(apartmentItemEntity: ApartmentItemEntity): Completable

    @Query("DELETE FROM apartment_list_item_entities WHERE id=:apartmentId")
    fun deleteApartmentItem(apartmentId: Int): Completable

    @Query("SELECT * FROM apartment_list_item_entities WHERE id=:apartmentId LIMIT 1")
    fun getApartmentItem(apartmentId: Int): Single<ApartmentItemEntity>
}