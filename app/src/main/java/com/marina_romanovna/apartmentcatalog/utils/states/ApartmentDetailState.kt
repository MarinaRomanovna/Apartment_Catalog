package com.marina_romanovna.apartmentcatalog.utils.states

import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem

sealed class ApartmentDetailState {
    object LoadingState : ApartmentDetailState()
    class SuccessState(val data: ApartmentItem) : ApartmentDetailState()
    class ErrorState(val error: Throwable) : ApartmentDetailState()
}