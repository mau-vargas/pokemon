package com.android.pokemon.domain.entity.evolution

data class EvolvesTo(
    val evolves_to: List<EvolvesToX>,
    val is_baby: Boolean,
    val species: SpeciesX
)