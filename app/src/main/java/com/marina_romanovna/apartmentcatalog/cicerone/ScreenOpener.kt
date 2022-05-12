package com.marina_romanovna.apartmentcatalog.cicerone

import com.github.terrakok.cicerone.Screen

interface ScreenOpener {

    fun navigateToLoginActivity(): Screen

    fun navigateToLoginFragment(): Screen

    fun navigateToApartmentActivity(): Screen

    fun navigateToApartmentListFragment(): Screen

    fun navigateToApartmentDetailFragment(apartmentId: Int): Screen

    fun navigateToAddApartmentDetailFragment(): Screen

    fun navigateToEditApartmentDetailFragment(apartmentId: Int): Screen

}