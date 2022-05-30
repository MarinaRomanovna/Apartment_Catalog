package com.marina_romanovna.apartmentcatalog.presentation.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.marina_romanovna.apartmentcatalog.ApartmentApplication
import com.marina_romanovna.apartmentcatalog.R
import com.marina_romanovna.apartmentcatalog.cicerone.ScreenOpener
import com.marina_romanovna.apartmentcatalog.databinding.LoginActivityBinding
import com.marina_romanovna.apartmentcatalog.domain.UserPreferences
import com.marina_romanovna.apartmentcatalog.utils.observe
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var screenOpener: ScreenOpener

    @Inject
    lateinit var userPreferences: UserPreferences

    private val navigator = AppNavigator(this, R.id.loginContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActivityBinding.inflate(layoutInflater).root.let(::setContentView)
        ApartmentApplication.dagger.inject(this)

        userPreferences.authToken.observe(lifecycleScope) { token ->
            if (token == null) {
                router.replaceScreen(screenOpener.navigateToLoginFragment())
            } else {
                router.replaceScreen(screenOpener.navigateToApartmentListFragment())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}