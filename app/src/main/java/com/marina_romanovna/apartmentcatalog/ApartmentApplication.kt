package com.marina_romanovna.apartmentcatalog

import android.app.Application
import com.marina_romanovna.apartmentcatalog.di.ApplicationComponent
import com.marina_romanovna.apartmentcatalog.di.DaggerApplicationComponent
import timber.log.Timber

class ApartmentApplication : Application() {

    companion object {
        private lateinit var appComponent: ApplicationComponent
        val dagger: ApplicationComponent
            get() = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appComponent = DaggerApplicationComponent.factory().create(this)
    }
}