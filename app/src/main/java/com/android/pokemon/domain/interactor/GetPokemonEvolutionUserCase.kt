package com.android.pokemon.domain.interactor

import com.android.pokemon.data.db.PokemonEntity
import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.GetPokemonRequest
import com.android.pokemon.domain.entity.evolution.prueba
import com.android.pokemon.domain.util.BaseUseCase
import javax.inject.Inject

class GetPokemonEvolutionUserCase @Inject constructor(private val repository: IDataRepository) :
    BaseGetPokemonEvolutionUserCase() {

    override suspend fun prepareExecuteOnBackground(params: String): prueba =
        repository.getPokemonEvolution(params)
}

typealias BaseGetPokemonEvolutionUserCase = BaseUseCase<String, prueba>