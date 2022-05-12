package com.marina_romanovna.apartmentcatalog.presentation.apartment.addedititem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.marina_romanovna.apartmentcatalog.cicerone.ScreenOpener
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import com.marina_romanovna.apartmentcatalog.domain.usecases.AddApartmentItemUseCase
import com.marina_romanovna.apartmentcatalog.domain.usecases.EditApartmentItemUseCase
import com.marina_romanovna.apartmentcatalog.domain.usecases.GetApartmentItemUseCase
import com.marina_romanovna.apartmentcatalog.domain.usecases.ValidateInputFieldsUseCase
import com.marina_romanovna.apartmentcatalog.domain.usecases.ValidateInputFieldsUseCase.Companion.DEFAULT_DOUBLE
import com.marina_romanovna.apartmentcatalog.domain.usecases.ValidateInputFieldsUseCase.Companion.DEFAULT_INT
import com.marina_romanovna.apartmentcatalog.domain.usecases.ValidateInputFieldsUseCase.Companion.DEFAULT_STRING
import com.marina_romanovna.apartmentcatalog.utils.*
import com.marina_romanovna.apartmentcatalog.utils.states.AddEditApartmentItemState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

class AddEditApartmentItemViewModel @Inject constructor(
    private val addApartmentItemUseCase: AddApartmentItemUseCase,
    private val getApartmentItemUseCase: GetApartmentItemUseCase,
    private val editApartmentItemUseCase: EditApartmentItemUseCase,
    private val validateInputFieldsUseCase: ValidateInputFieldsUseCase,
    private val router: Router,
    private val screenOpener: ScreenOpener
) : BaseViewModel() {

    private val _state = event<AddEditApartmentItemState>(AddEditApartmentItemState.BeginnerState)
    val state: StateFlow<AddEditApartmentItemState> = _state.asStateFlow()

    private val _errorInputFields = event(false)
    val errorInputFields: StateFlow<Boolean> = _errorInputFields.asStateFlow()

    private val _apartmentItem = eventWithNull<ApartmentItem>(null)
    val apartmentItem: StateFlow<ApartmentItem?> = _apartmentItem.asStateFlow()

    fun onClickDone() {
        router.replaceScreen(screenOpener.navigateToApartmentListFragment())
    }

    fun getApartmentItem(apartmentId: Int) {
        getApartmentItemUseCase.execute(apartmentId)
            .doOnSubscribe { AddEditApartmentItemState.LoadingState }
            .get(
                disposable = disposables,
                onError = { error ->
                    _state.value = AddEditApartmentItemState.ErrorState
                    Timber.d(error.message.toString())
                },
                onSuccess = { apartmentItem ->
                    _apartmentItem.value = apartmentItem
                }
            )
    }

    fun addApartmentItem(
        inputAddress: String?,
        inputPrice: String?,
        inputSquare: String?,
        inputNumberOfRooms: String?,
        inputFloor: String?
    ) {
        val apartmentItem = getApartmentWithParseFields(
            inputAddress = inputAddress,
            inputPrice = inputPrice,
            inputSquare = inputSquare,
            inputNumberOfRooms = inputNumberOfRooms,
            inputFloor = inputFloor
        )
        validateInputFieldsUseCase.execute(apartmentItem)
            .get(disposable = disposables,
                onError = { error ->
                    _state.value = AddEditApartmentItemState.ErrorState
                    Timber.d(error.message.toString())
                },
                onSuccess = { isValid ->
                    _errorInputFields.value = false
                    if (isValid) {
                        addApartmentItemUseCase.execute(apartmentItem)
                            .doOnComplete { AddEditApartmentItemState.LoadingState }
                            .get(
                                disposable = disposables,
                                onError = { error ->
                                    _state.value = AddEditApartmentItemState.ErrorState
                                    Timber.d(error.message.toString())
                                },
                                onComplete = {
                                    _state.value = AddEditApartmentItemState.SuccessState
                                }
                            )
                    } else {
                        _errorInputFields.value = true
                    }
                })
    }

    fun editApartmentItem(
        inputAddress: String?,
        inputPrice: String?,
        inputSquare: String?,
        inputNumberOfRooms: String?,
        inputFloor: String?
    ) {
        val apartmentItem = getApartmentWithParseFields(
            inputAddress = inputAddress,
            inputPrice = inputPrice,
            inputSquare = inputSquare,
            inputNumberOfRooms = inputNumberOfRooms,
            inputFloor = inputFloor
        )
        validateInputFieldsUseCase.execute(apartmentItem)
            .get(disposable = disposables,
                onError = { error ->
                    _state.value = AddEditApartmentItemState.ErrorState
                    Timber.d(error.message.toString())
                },
                onSuccess = { isValid ->
                    _errorInputFields.value = false
                    if (isValid) {
                        apartmentItem.id = _apartmentItem.value?.id ?: ApartmentItem.UNDEFINED_ID
                        editApartmentItemUseCase.execute(apartmentItem)
                            .doOnComplete { AddEditApartmentItemState.LoadingState }
                            .get(
                                disposable = disposables,
                                onError = { error ->
                                    _state.value = AddEditApartmentItemState.ErrorState
                                    Timber.d(error.message.toString())
                                },
                                onComplete = {
                                    _state.value = AddEditApartmentItemState.SuccessState
                                    onClickDone()
                                }
                            )
                    } else {
                        _errorInputFields.value = true
                    }
                })
    }

    private fun getApartmentWithParseFields(
        inputAddress: String?,
        inputPrice: String?,
        inputSquare: String?,
        inputNumberOfRooms: String?,
        inputFloor: String?
    ): ApartmentItem {
        val address = inputAddress ?: DEFAULT_STRING
        val price = parseStringToInt(inputPrice, DEFAULT_INT)
        val square = parseStringToDouble(inputSquare, DEFAULT_DOUBLE)
        val rooms = parseStringToInt(inputNumberOfRooms, DEFAULT_INT)
        val floor = parseStringToInt(inputFloor, DEFAULT_INT)
        val pricePerSquareMeter = price / square
        val photo = DEFAULT_STRING //TODO("реализовать загрузку фото")
        return ApartmentItem(
            photo = photo,
            price = price,
            square = square,
            address = address,
            numberOfRooms = rooms,
            pricePerSquareMeter = pricePerSquareMeter,
            floor = floor
        )
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val addApartmentItemUseCase: AddApartmentItemUseCase,
        private val getApartmentItemUseCase: GetApartmentItemUseCase,
        private val editApartmentItemUseCase: EditApartmentItemUseCase,
        private val validateInputFieldsUseCase: ValidateInputFieldsUseCase,
        private val router: Router,
        private val screenOpener: ScreenOpener
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddEditApartmentItemViewModel(
                addApartmentItemUseCase = addApartmentItemUseCase,
                getApartmentItemUseCase = getApartmentItemUseCase,
                editApartmentItemUseCase = editApartmentItemUseCase,
                validateInputFieldsUseCase = validateInputFieldsUseCase,
                router = router,
                screenOpener = screenOpener
            ) as T
        }
    }
}