package com.android.pokemon.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.pokemon.presentation.ui.PokedexViewModel
import com.android.pokemon.presentation.util.ViewModelFactory
import com.android.pokemon.presentation.util.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PokedexViewModel::class)
    abstract fun bindMainScreenViewModel(viewModel: PokedexViewModel): ViewModel

}