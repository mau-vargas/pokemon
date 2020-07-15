package com.android.pokemon.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokedexDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemons(pokemon: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon")
    fun getLocalPokemons(): List<PokemonEntity>

    @Query("DELETE FROM pokemon")
    fun deleteLocalPokemons()
}