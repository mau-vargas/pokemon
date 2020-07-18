package com.android.pokemon.presentation.di.module

import android.content.Context
import androidx.room.Room
import com.android.pokemon.data.db.PokemonDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context.applicationContext
    }

    @Provides
    fun providesCounterDatabase(context: Context): PokemonDatabase =
        Room.databaseBuilder(context, PokemonDatabase::class.java, "pokedex.db")
            .allowMainThreadQueries().build()

    @Provides
    fun providesCounterDao(database: PokemonDatabase) = database.pokemonDao()

}