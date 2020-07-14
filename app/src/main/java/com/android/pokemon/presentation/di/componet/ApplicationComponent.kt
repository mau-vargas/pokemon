package com.android.pokemon.presentation.di.componet

import android.app.Application
import com.android.pokemon.presentation.di.module.MainModule
import com.android.pokemon.presentation.di.module.RepositoryModule
import com.android.pokemon.presentation.di.module.RetrofitModule
import com.android.pokemon.presentation.di.module.ViewModelModule
import com.android.pokemon.presentation.ui.PokedexFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainModule::class,
        RepositoryModule::class,
        RetrofitModule::class,
        ViewModelModule::class]
)
interface ApplicationComponent : FragmentInjector {
    fun inject(application: Application)
}

interface FragmentInjector {
    fun inject(pokedexFragment: PokedexFragment)

}
