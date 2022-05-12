package com.marina_romanovna.apartmentcatalog.utils.states

import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem

sealed class ApartmentListState {
    object EmptyListState : ApartmentListState()
    object LoadingState : ApartmentListState()
    class SuccessState(val data: List<ApartmentItem>) : ApartmentListState()
    class ErrorState(val error: Throwable) : ApartmentListState()
}