package com.android.pokemon.data.network


import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.entity.evolution.Evolution
import com.android.pokemon.domain.entity.Detail.Detail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET(ServicesConstants.GET_POKEMON_PATH)
    fun getPokemonServices(): Call<GetPokemonsResponse>

    @GET(ServicesConstants.GET_POKEMON_DETAIL_PATH + "/{id}")
    fun getPokemonDetailServices(@Path("id") name: String): Call<Detail>

    @GET(ServicesConstants.GET_POKEMON_EVOLUTION + "/{id}")
    fun getPokemonEvolutionService(@Path("id") name: String): Call<Evolution>

}