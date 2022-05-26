package com.marina_romanovna.apartmentcatalog.di

import android.app.Application
import com.marina_romanovna.apartmentcatalog.cicerone.CiceroneModule
import com.marina_romanovna.apartmentcatalog.presentation.apartment.ApartmentActivity
import com.marina_romanovna.apartmentcatalog.presentation.apartment.addedititem.AddEditApartmentItemFragment
import com.marina_romanovna.apartmentcatalog.presentation.apartment.detail.ApartmentDetailFragment
import com.marina_romanovna.apartmentcatalog.presentation.apartment.list.ApartmentListFragment
import com.marina_romanovna.apartmentcatalog.presentation.login.LoginActivity
import com.marina_romanovna.apartmentcatalog.presentation.login.LoginFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        CiceroneModule::class,
        RoomModule::class,
        NetworkModule::class
    ]
)
interface ApplicationComponent {

    fun inject(loginActivity: LoginActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(apartmentActivity: ApartmentActivity)
    fun inject(apartmentListFragment: ApartmentListFragment)
    fun inject(addEditApartmentItemFragment: AddEditApartmentItemFragment)
    fun inject(apartmentDetailFragment: ApartmentDetailFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}