package com.android.pokemon.domain

import com.android.pokemon.domain.entity.GetPokemonsResponse

interface IDataRepository {
    @Throws(Exception::class)
    suspend fun getPokemons(): GetPokemonsResponse

}