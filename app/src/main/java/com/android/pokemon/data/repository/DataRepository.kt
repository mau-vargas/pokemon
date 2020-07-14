package com.android.pokemon.data.repository

import com.android.pokemon.data.network.APIService
import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.GetPokemonsResponse
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: APIService): IDataRepository {

    override suspend fun getPokemons(): GetPokemonsResponse {
        return apiService.getPokemonsServices().execute().body()!!
    }

}