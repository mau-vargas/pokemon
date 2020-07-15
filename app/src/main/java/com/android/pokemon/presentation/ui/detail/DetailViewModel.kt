package com.android.pokemon.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import com.android.pokemon.domain.entity.GetPokemonDetailRequest
import com.android.pokemon.domain.entity.GetPokemonRequest
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.interactor.GetPokemonDetailUserCase
import com.android.pokemon.presentation.util.BaseViewModel
import com.android.pokemon.presentation.util.Failure
import com.android.pokemon.presentation.util.Resource
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val getPokemonDetailUserCase: GetPokemonDetailUserCase) :
    BaseViewModel() {

    var liveGetPokemonDetail: MutableLiveData<Resource<GetPokemonsResponse>> = MutableLiveData()


    fun getPokemonDetail(name: String) = with(liveGetPokemonDetail) {
        postLoading()

        fun onSuccess(data: GetPokemonsResponse) {
            postSuccess(data)
        }

        fun onFailure(failure: Failure) {
            postFailure(failure)
        }

        getPokemonDetailUserCase.invoke(name, ::onSuccess, ::onFailure)
    }
}