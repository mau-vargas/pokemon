package com.android.pokemon.presentation.ui.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.android.pokemon.domain.entity.GetPokemonRequest
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.interactor.GetPokemonUserCase
import com.android.pokemon.presentation.util.BaseViewModel
import com.android.pokemon.presentation.util.Failure
import com.android.pokemon.presentation.util.Resource
import com.android.pokemon.presentation.util.look
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val getPokemonUserCase: GetPokemonUserCase) :
    BaseViewModel() {

    var liveGetPokemons: MutableLiveData<Resource<GetPokemonsResponse>> = MutableLiveData()


    fun getPokemon() = with(liveGetPokemons) {
        postLoading()

        fun onSuccess(data: GetPokemonsResponse) {
            postSuccess(data)
        }

        fun onFailure(failure: Failure) {
            postFailure(failure)
        }

        getPokemonUserCase.invoke(GetPokemonRequest(), ::onSuccess, ::onFailure)
    }



}