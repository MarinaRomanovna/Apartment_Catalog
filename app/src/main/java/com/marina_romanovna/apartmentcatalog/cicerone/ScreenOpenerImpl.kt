package com.marina_romanovna.apartmentcatalog.cicerone

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.marina_romanovna.apartmentcatalog.presentation.apartment.ApartmentActivity
import com.marina_romanovna.apartmentcatalog.presentation.apartment.addedititem.AddEditApartmentItemFragment
import com.marina_romanovna.apartmentcatalog.presentation.apartment.detail.ApartmentDetailFragment
import com.marina_romanovna.apartmentcatalog.presentation.apartment.list.ApartmentListFragment
import com.marina_romanovna.apartmentcatalog.presentation.login.LoginFragment
import com.marina_romanovna.apartmentcatalog.presentation.login.RegisterFragment
import javax.inject.Inject

class ScreenOpenerImpl @Inject constructor() : ScreenOpener {

    override fun navigateToLoginActivity(): Screen {
        return ActivityScreen {
            ApartmentActivity.newIntent(it)
        }
    }

    override fun navigateToLoginFragment(): Screen {
        return FragmentScreen {
            LoginFragment.newInstance()
        }
    }

    override fun navigateToRegisterFragment(): Screen {
        return FragmentScreen {
            RegisterFragment.newInstance()
        }
    }

    override fun navigateToApartmentActivity(): Screen {
        return ActivityScreen {
            ApartmentActivity.newIntent(it)
        }
    }

    override fun navigateToApartmentListFragment(): Screen {
        return FragmentScreen {
            ApartmentListFragment.newInstance()
        }
    }

    override fun navigateToApartmentDetailFragment(apartmentId: Int): Screen {
        return FragmentScreen {
            ApartmentDetailFragment.newInstance(apartmentId)
        }
    }

    override fun navigateToAddApartmentDetailFragment(): Screen {
        return FragmentScreen {
            AddEditApartmentItemFragment.newInstanceAddItem()
        }
    }

    override fun navigateToEditApartmentDetailFragment(apartmentId: Int): Screen {
        return FragmentScreen {
            AddEditApartmentItemFragment.newInstanceEditItem(apartmentId)
        }
    }
}