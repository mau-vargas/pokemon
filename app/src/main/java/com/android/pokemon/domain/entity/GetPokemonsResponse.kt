package com.android.pokemon.domain.entity

import com.google.gson.annotations.SerializedName



class GetPokemonsResponse : ArrayList<PokemonItem>()

data class PokemonItem(
    @field:SerializedName("count")
    val count: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("id")
    val counterId: String
)
