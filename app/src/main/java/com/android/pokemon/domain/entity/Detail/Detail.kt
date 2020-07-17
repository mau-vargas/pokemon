package com.android.pokemon.domain.entity.Detail

data class Detail(
    val abilities: ArrayList<Ability>,
    val location_area_encounters: String,
    val moves: ArrayList<Move>,
    val types: ArrayList<Type>,
    val height: Int,
    val weight: Int,
    val id: Int,
    val name: String
)