package com.android.pokemon.presentation.di.module

import com.android.pokemon.data.repository.DataRepository
import com.android.pokemon.domain.IDataRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindDataRepository(repository: DataRepository): IDataRepository
}
