package com.marina_romanovna.apartmentcatalog.utils.states

sealed class AddEditApartmentItemState {
    object BeginnerState : AddEditApartmentItemState()
    object LoadingState : AddEditApartmentItemState()
    object SuccessState : AddEditApartmentItemState()
    object ErrorState : AddEditApartmentItemState()
}