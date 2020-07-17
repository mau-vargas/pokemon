package com.android.pokemon.domain.entity.evolution

data class EvolvesToX(
    val evolves_to: List<Any>,
    val is_baby: Boolean,
    val species: Species
)