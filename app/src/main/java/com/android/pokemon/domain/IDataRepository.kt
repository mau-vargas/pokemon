package com.android.pokemon.domain

import com.android.pokemon.data.db.PokemonEntity
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.entity.evolution.Evolution
import com.android.pokemon.domain.entity.Detail.Detail

interface IDataRepository {
    @Throws(Exception::class)
    suspend fun getPokemons(): GetPokemonsResponse

    @Throws(Exception::class)
    suspend fun getPokemonDetail(params: String): Detail


    @Throws(Exception::class)
    suspend fun getLocalPokemons(): List<PokemonEntity>

    @Throws(Exception::class)
    suspend fun getPokemonEvolution(name: String): Evolution




}