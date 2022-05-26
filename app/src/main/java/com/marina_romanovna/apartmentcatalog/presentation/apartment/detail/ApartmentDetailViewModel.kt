package com.marina_romanovna.apartmentcatalog.presentation.apartment.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.marina_romanovna.apartmentcatalog.cicerone.ScreenOpener
import com.marina_romanovna.apartmentcatalog.domain.usecases.GetApartmentItemUseCase
import com.marina_romanovna.apartmentcatalog.utils.BaseViewModel
import com.marina_romanovna.apartmentcatalog.utils.event
import com.marina_romanovna.apartmentcatalog.utils.get
import com.marina_romanovna.apartmentcatalog.utils.states.ApartmentDetailState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

class ApartmentDetailViewModel @Inject constructor(
    private val getApartmentItemUseCase: GetApartmentItemUseCase,
    private val router: Router,
    private val screenOpener: ScreenOpener
) : BaseViewModel() {

    private val _state = event<ApartmentDetailState>(ApartmentDetailState.LoadingState)
    val state: StateFlow<ApartmentDetailState> = _state.asStateFlow()

    fun onEditClick(apartmentId: Int) {
        router.replaceScreen(screenOpener.navigateToEditApartmentDetailFragment(apartmentId))
    }

    fun getApartmentDetail(apartmentId: Int) {
        getApartmentItemUseCase.execute(apartmentId)
            .get(
                disposable = disposables,
                onError = { error ->
                    _state.value = ApartmentDetailState.ErrorState(error)
                    Timber.d(error.message.toString())
                },
                onSuccess = { apartmentItem ->
                    _state.value = ApartmentDetailState.SuccessState(apartmentItem)
                }
            )
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getApartmentItemUseCase: GetApartmentItemUseCase,
        private val router: Router,
        private val screenOpener: ScreenOpener
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ApartmentDetailViewModel(
                getApartmentItemUseCase = getApartmentItemUseCase,
                router = router,
                screenOpener = screenOpener
            ) as T
        }
    }
}