package com.android.pokemon.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.pokemon.presentation.ui.detail.DetailViewModel
import com.android.pokemon.presentation.ui.main.PokedexViewModel
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
    abstract fun bindPokedexViewModel(viewModel: PokedexViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel


}