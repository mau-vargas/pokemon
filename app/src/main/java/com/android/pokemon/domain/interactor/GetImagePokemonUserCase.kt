package com.android.pokemon.domain.interactor

import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.GetPokemonRequest
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.util.BaseUseCase
import javax.inject.Inject


class GetImagePokemonUserCase @Inject constructor(private val repository: IDataRepository) :
    BaseGetImagePokemonUserCase() {

    override suspend fun prepareExecuteOnBackground(params: GetPokemonRequest): GetPokemonsResponse =
        repository.getPokemons()
}

typealias BaseGetImagePokemonUserCase = BaseUseCase<GetPokemonRequest, GetPokemonsResponse>