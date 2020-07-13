package com.android.pokemon.presentation.di.componet

import android.app.Application

interface ApplicationComponent : FragmentInjector {
    fun inject(application: Application)
}

interface FragmentInjector {

}
