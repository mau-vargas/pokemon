package com.android.pokemon.presentation.util

import com.android.pokemon.R


enum class PokemonImageType(private val value: Int) {
    bug(R.drawable.ic_bug),
    dark(R.drawable.ic_dark),
    dragon(R.drawable.ic_dragon),
    electric(R.drawable.ic_electric),
    fairy(R.drawable.ic_fairy),
    fighting(R.drawable.ic_fighting),
    fire(R.drawable.ic_fire),
    flying(R.drawable.ic_flying),
    ghost(R.drawable.ic_ghost),
    grass(R.drawable.ic_grass),
    ground(R.drawable.ic_ground),
    ice(R.drawable.ic_ice),
    normal(R.drawable.ic_normal),
    poison(R.drawable.ic_poison),
    psychic(R.drawable.ic_psychic),
    rock(R.drawable.ic_rock),
    steel(R.drawable.ic_steel),
    water(R.drawable.ic_water);
    fun getValue(): Int = value
}


