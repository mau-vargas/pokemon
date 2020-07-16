package com.android.pokemon.domain.entity.Detail

data class Detail(
    val abilities: List<Ability>,
    val location_area_encounters: String,
    val moves: List<Move>,
    val types: List<Type>,

    val height: Int,
    val weight: Int,
    val id: Int,
    val name: String

)