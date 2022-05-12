package com.marina_romanovna.apartmentcatalog.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Router
import com.marina_romanovna.apartmentcatalog.cicerone.ScreenOpener
import com.marina_romanovna.apartmentcatalog.utils.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val router: Router,
    private val screenOpener: ScreenOpener
) : BaseViewModel() {

    fun onAuthClick() {
        router.navigateTo(screenOpener.navigateToApartmentActivity())
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val router: Router,
        private val screenOpener: ScreenOpener
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(
                router = router,
                screenOpener = screenOpener
            ) as T
        }
    }
}