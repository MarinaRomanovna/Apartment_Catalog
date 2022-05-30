package com.marina_romanovna.apartmentcatalog.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.marina_romanovna.apartmentcatalog.cicerone.ScreenOpener
import com.marina_romanovna.apartmentcatalog.data.responses.LoginResponse
import com.marina_romanovna.apartmentcatalog.domain.usecases.LoginUseCase
import com.marina_romanovna.apartmentcatalog.utils.BaseViewModel
import com.marina_romanovna.apartmentcatalog.utils.eventWithNull
import com.marina_romanovna.apartmentcatalog.utils.get
import com.marina_romanovna.apartmentcatalog.utils.states.LoginState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val router: Router,
    private val screenOpener: ScreenOpener,
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _state = eventWithNull<LoginState<LoginResponse>?>(null)
    val state: StateFlow<LoginState<LoginResponse>?> = _state.asStateFlow()

    fun onAuthClick() {
        router.replaceScreen(screenOpener.navigateToApartmentActivity())
    }

    fun login(email: String, password: String) {
        loginUseCase.execute(email, password)
            .get(disposables,
                onError = { throwable ->
                    when (throwable) {
                        is HttpException -> {
                            _state.value = LoginState.Failure(
                                isNetworkError = false,
                                errorCode = throwable.code(),
                                errorBody = throwable.response()?.errorBody()
                            )
                        }
                        else -> {
                            _state.value = LoginState.Failure(
                                isNetworkError = true,
                                errorCode = null,
                                errorBody = null
                            )
                        }
                    }
                },
                onSuccess = { loginResponse ->
                    _state.value = LoginState.Success(loginResponse)
                })
    }

    fun saveAuthToken(authToken: String): Job {
        return viewModelScope.launch {
            loginUseCase.saveAuthToken(authToken)
        }
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