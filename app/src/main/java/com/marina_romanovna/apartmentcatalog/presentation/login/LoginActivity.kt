package com.marina_romanovna.apartmentcatalog.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.marina_romanovna.apartmentcatalog.ApartmentApplication
import com.marina_romanovna.apartmentcatalog.R
import com.marina_romanovna.apartmentcatalog.cicerone.ScreenOpener
import com.marina_romanovna.apartmentcatalog.data.userpreferences.UserPreferencesImpl
import com.marina_romanovna.apartmentcatalog.databinding.LoginActivityBinding
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var screenOpener: ScreenOpener

    private val navigator = AppNavigator(this, R.id.loginContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActivityBinding.inflate(layoutInflater).root.let(::setContentView)
        ApartmentApplication.dagger.inject(this)

        if (savedInstanceState == null) {
            router.replaceScreen(screenOpener.navigateToLoginFragment())
        }

        val userPreferences = UserPreferencesImpl(this)

        userPreferences.authToken.asLiveData().observe(this) {
            Toast.makeText(this, it ?: "token is null", Toast.LENGTH_LONG).show()
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