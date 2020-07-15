package com.android.pokemon.data.repository

import com.android.pokemon.data.network.APIService
import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.GetPokemonDetailRequest
import com.android.pokemon.domain.entity.GetPokemonsResponse
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: APIService): IDataRepository {

    override suspend fun getPokemons(): GetPokemonsResponse {
        return apiService.getPokemonServices().execute().body()!!
    }

    override suspend fun getPokemonDetail(name: String): GetPokemonsResponse {
        return apiService.getPokemonDetailServices(name).execute().body()!!
    }



}