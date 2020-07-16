package com.android.pokemon.data.repository

import android.content.Context
import com.android.pokemon.data.db.PokedexDao
import com.android.pokemon.data.db.PokemonEntity
import com.android.pokemon.data.network.APIService
import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.entity.evolution.Evolution
import com.android.pokemon.domain.entity.Detail.Detail
import javax.inject.Inject

class DataRepository @Inject constructor(
    var context: Context,
    private val apiService: APIService,
    private val pokedexDao: PokedexDao
) : IDataRepository {

    override suspend fun getPokemons(): GetPokemonsResponse {
        val response = apiService.getPokemonServices().execute().body()!!
        pokedexDao.insertPokemons(response.pokemon_species)
        return response
    }

    override suspend fun getPokemonDetail(name: String): Detail {
        return apiService.getPokemonDetailServices(name).execute().body()!!
    }

    override suspend fun getLocalPokemons(): List<PokemonEntity> {
        return pokedexDao.getLocalPokemons()
    }

    override suspend fun getPokemonEvolution(name: String): Evolution {
        return apiService.getPokemonEvolutionService(name).execute().body()!!
    }





}