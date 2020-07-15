package com.android.pokemon.domain.interactor

import com.android.pokemon.domain.IDataRepository
import com.android.pokemon.domain.entity.prueba.Borrar
import com.android.pokemon.domain.util.BaseUseCase
import javax.inject.Inject


class GetPokemonDetailUserCase @Inject constructor(private val repository: IDataRepository) :
    BaseGetPokemonDetailUserCase() {

    override suspend fun prepareExecuteOnBackground(params: String): Borrar =
        repository.getPokemonDetail(params)
}

typealias BaseGetPokemonDetailUserCase = BaseUseCase<String, Borrar>