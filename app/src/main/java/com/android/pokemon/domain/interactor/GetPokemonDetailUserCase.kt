package com.android.pokemon.domain.interactor

import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.Detail.Detail
import com.android.pokemon.domain.util.BaseUseCase
import javax.inject.Inject


class GetPokemonDetailUserCase @Inject constructor(private val repository: IDataRepository) :
    BaseGetPokemonDetailUserCase() {

    override suspend fun prepareExecuteOnBackground(params: String): Detail =
        repository.getPokemonDetail(params)
}

typealias BaseGetPokemonDetailUserCase = BaseUseCase<String, Detail>