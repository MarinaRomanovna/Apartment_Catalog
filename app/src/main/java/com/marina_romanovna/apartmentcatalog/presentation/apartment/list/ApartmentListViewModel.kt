package com.marina_romanovna.apartmentcatalog.presentation.apartment.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.marina_romanovna.apartmentcatalog.cicerone.ScreenOpener
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import com.marina_romanovna.apartmentcatalog.domain.usecases.DeleteApartmentItemUseCase
import com.marina_romanovna.apartmentcatalog.domain.usecases.GetApartmentListUseCase
import com.marina_romanovna.apartmentcatalog.utils.BaseViewModel
import com.marina_romanovna.apartmentcatalog.utils.event
import com.marina_romanovna.apartmentcatalog.utils.get
import com.marina_romanovna.apartmentcatalog.utils.states.ApartmentListState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

class ApartmentListViewModel @Inject constructor(
    private val getApartmentListUseCase: GetApartmentListUseCase,
    private val deleteApartmentItemUseCase: DeleteApartmentItemUseCase,
    private val router: Router,
    private val screenOpener: ScreenOpener
) : BaseViewModel() {

    private val _state = event<ApartmentListState>(ApartmentListState.LoadingState)
    val state: StateFlow<ApartmentListState> = _state.asStateFlow()

    fun onExitClick() {
        router.replaceScreen(screenOpener.navigateToLoginFragment())
    }

    fun onAddApartmentClick() {
        router.navigateTo(screenOpener.navigateToAddApartmentDetailFragment())
    }

    fun onApartmentItemClick(apartmentId: Int) {
        router.navigateTo(screenOpener.navigateToApartmentDetailFragment(apartmentId))
    }

    fun getApartmentList() {
        getApartmentListUseCase.execute()
            .doOnSubscribe { _state.value = ApartmentListState.LoadingState }
            .get(
                disposable = disposables,
                onError = { error ->
                    _state.value = ApartmentListState.ErrorState(error)
                    Timber.d(error.message.toString())
                },
                onSuccess = { apartmentList ->
                    if (apartmentList.isEmpty()) {
                        _state.value = ApartmentListState.EmptyListState
                    } else {
                        _state.value = ApartmentListState.SuccessState(apartmentList)
                    }
                }
            )
    }

    fun deleteApartmentItem(apartmentItem: ApartmentItem) {
        deleteApartmentItemUseCase.execute(apartmentItem)
            .get(
                disposable = disposables,
                onError = { error ->
                    _state.value = ApartmentListState.ErrorState(error)
                    Timber.d(error.message.toString())
                },
                onComplete = {
                    getApartmentList()
                }
            )
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getApartmentListUseCase: GetApartmentListUseCase,
        private val deleteApartmentItemUseCase: DeleteApartmentItemUseCase,
        private val router: Router,
        private val screenOpener: ScreenOpener
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ApartmentListViewModel(
                getApartmentListUseCase = getApartmentListUseCase,
                deleteApartmentItemUseCase = deleteApartmentItemUseCase,
                router = router,
                screenOpener = screenOpener
            ) as T
        }
    }
}