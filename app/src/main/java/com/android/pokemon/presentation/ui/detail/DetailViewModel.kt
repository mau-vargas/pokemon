package com.android.pokemon.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import com.android.pokemon.domain.entity.evolution.Evolution
import com.android.pokemon.domain.entity.Detail.Detail
import com.android.pokemon.domain.interactor.GetPokemonDetailUserCase
import com.android.pokemon.domain.interactor.GetPokemonEvolutionUserCase
import com.android.pokemon.presentation.util.BaseViewModel
import com.android.pokemon.presentation.util.Failure
import com.android.pokemon.presentation.util.Resource
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getPokemonDetailUserCase: GetPokemonDetailUserCase,
    private val getPokemonEvolutionUserCase: GetPokemonEvolutionUserCase
) :
    BaseViewModel() {

    var liveGetPokemonDetail: MutableLiveData<Resource<Detail>> = MutableLiveData()
    var liveGetPokemonEvolution: MutableLiveData<Resource<Evolution>> = MutableLiveData()


    val pokemonEvolution: Evolution?
        get() {
            return liveGetPokemonEvolution.value?.data
        }


    fun getPokemonDetail(name: String) = with(liveGetPokemonDetail) {
        postLoading()

        fun onSuccess(data: Detail) {
            postSuccess(data)
        }

        fun onFailure(failure: Failure) {
            postFailure(failure)
        }

        getPokemonDetailUserCase.invoke(name, ::onSuccess, ::onFailure)
    }


    fun getPokemonEvolution(name: String) = with(liveGetPokemonEvolution) {
        postLoading()

        fun onSuccess(data: Evolution) {
            postSuccess(data)
        }

        fun onFailure(failure: Failure) {
            postFailure(failure)
        }

        getPokemonEvolutionUserCase.invoke(name, ::onSuccess, ::onFailure)
    }
}