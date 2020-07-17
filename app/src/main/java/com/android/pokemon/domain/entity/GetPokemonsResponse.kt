package com.android.pokemon.domain.entity

import com.android.pokemon.data.db.PokemonEntity
import com.google.gson.annotations.SerializedName


data class GetPokemonsResponse(
    val id: Int,
    val pokemon_species: ArrayList<PokemonEntity>
)