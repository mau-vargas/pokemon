package com.android.pokemon.domain.interactor

import com.android.pokemon.data.db.PokemonEntity
import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.GetPokemonRequest
import com.android.pokemon.domain.util.BaseUseCase
import javax.inject.Inject

class GetLocalPokemonUserCase @Inject constructor(private val repository: IDataRepository) :
    BaseGetLocalPokemonUserCase() {

    override suspend fun prepareExecuteOnBackground(params: GetPokemonRequest): List<PokemonEntity> =
        repository.getLocalPokemons()
}

typealias BaseGetLocalPokemonUserCase = BaseUseCase<GetPokemonRequest, List<PokemonEntity>>