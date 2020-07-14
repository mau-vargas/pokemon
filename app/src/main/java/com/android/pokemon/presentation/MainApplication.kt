package com.android.pokemon.presentation

import android.app.Application
import com.android.pokemon.presentation.di.componet.ApplicationComponent
import com.android.pokemon.presentation.di.componet.DaggerApplicationComponent

class MainApplication : Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .build()

        appComponent().inject(this)
    }

}
fun appComponent() = MainApplication.applicationComponent


