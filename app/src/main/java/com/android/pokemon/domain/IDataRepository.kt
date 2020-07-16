package com.android.pokemon.domain

import com.android.pokemon.data.db.PokemonEntity
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.entity.prueba.Borrar

interface IDataRepository {
    @Throws(Exception::class)
    suspend fun getPokemons(): GetPokemonsResponse

    @Throws(Exception::class)
    suspend fun getPokemonDetail(params: String): Borrar


    @Throws(Exception::class)
    suspend fun getLocalPokemons(): List<PokemonEntity>




}