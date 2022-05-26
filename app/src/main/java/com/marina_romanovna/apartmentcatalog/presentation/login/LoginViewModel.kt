package com.marina_romanovna.apartmentcatalog.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.marina_romanovna.apartmentcatalog.cicerone.ScreenOpener
import com.marina_romanovna.apartmentcatalog.domain.usecases.LoginUseCase
import com.marina_romanovna.apartmentcatalog.utils.BaseViewModel
import com.marina_romanovna.apartmentcatalog.utils.event
import com.marina_romanovna.apartmentcatalog.utils.get
import com.marina_romanovna.apartmentcatalog.utils.states.LoginState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val router: Router,
    private val screenOpener: ScreenOpener,
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _state = event<LoginState>(LoginState.Loading)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onAuthClick() {
        router.replaceScreen(screenOpener.navigateToApartmentActivity())
    }

    fun login(email: String, password: String) {
        loginUseCase.execute(email, password)
            .get(disposables,
                onError = {
                    _state.value = LoginState.Error
                },
                onComplete = {
                    _state.value = LoginState.Success
                })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val router: Router,
        private val screenOpener: ScreenOpener,
        private val loginUseCase: LoginUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(
                router = router,
                screenOpener = screenOpener,
                loginUseCase = loginUseCase
            ) as T
        }
    }
}