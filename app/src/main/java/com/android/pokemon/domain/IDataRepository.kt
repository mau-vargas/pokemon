package com.android.pokemon.domain

import com.android.pokemon.domain.entity.GetPokemonDetailRequest
import com.android.pokemon.domain.entity.GetPokemonsResponse

interface IDataRepository {
    @Throws(Exception::class)
    suspend fun getPokemons(): GetPokemonsResponse

    @Throws(Exception::class)
    suspend fun getPokemonDetail(params: String): GetPokemonsResponse

}