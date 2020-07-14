package com.android.pokemon.domain.interactor

import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.GetPokemonRequest
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.util.BaseUseCase
import javax.inject.Inject

class GetPokemonUserCase @Inject constructor(private val repository: IDataRepository) :
    BaseDesCounterUserCase() {

    override suspend fun prepareExecuteOnBackground(params: GetPokemonRequest): GetPokemonsResponse =
        repository.getPokemons()
}

typealias BaseDesCounterUserCase = BaseUseCase<GetPokemonRequest, GetPokemonsResponse>