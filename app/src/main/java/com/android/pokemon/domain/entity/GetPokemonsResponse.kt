package com.android.pokemon.domain.entity

import com.android.pokemon.data.db.PokemonEntity

data class GetPokemonsResponse(
    val id: Int,
    val pokemon_species: ArrayList<PokemonEntity>
)