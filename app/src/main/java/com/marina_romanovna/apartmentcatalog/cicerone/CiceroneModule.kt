package com.marina_romanovna.apartmentcatalog.cicerone

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.marina_romanovna.apartmentcatalog.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class CiceroneModule {

    @Provides
    @ApplicationScope
    fun provideCicerone(): Cicerone<Router> {
        return Cicerone.create()
    }

    @Provides
    @ApplicationScope
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    @Provides
    @ApplicationScope
    fun provideRouter(cicerone: Cicerone<Router>): Router {
        return cicerone.router
    }

    @Provides
    @ApplicationScope
    fun provideScreenOpener(): ScreenOpener {
        return ScreenOpenerImpl()
    }
}