package com.android.pokemon.data.network

import com.android.pokemon.domain.entity.GetPokemonsResponse
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET(ServicesConstants.GET_POKEMONS_PATH)
    fun getPokemonsServices(): Call<GetPokemonsResponse>

}