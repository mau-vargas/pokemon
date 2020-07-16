package com.android.pokemon.domain.entity.prueba

data class Borrar(
    val abilities: List<Ability>,
    val location_area_encounters: String,
    val moves: List<Move>,
    val types: List<Type>,

    val height: Int,
    val weight: Int,
    val id: Int,
    val name: String

)