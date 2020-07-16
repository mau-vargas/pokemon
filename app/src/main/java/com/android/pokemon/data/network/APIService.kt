package com.android.pokemon.data.network


import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.entity.prueba.Borrar
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET(ServicesConstants.GET_POKEMON_PATH)
    fun getPokemonServices(): Call<GetPokemonsResponse>

    @GET(ServicesConstants.GET_POKEMON_DETAIL_PATH + "/{id}")
    fun getPokemonDetailServices(@Path("id") name: String): Call<Borrar>
}