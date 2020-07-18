package com.android.pokemon.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import com.android.pokemon.data.db.PokemonEntity
import com.android.pokemon.domain.entity.GetPokemonRequest
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.domain.interactor.GetLocalPokemonUserCase
import com.android.pokemon.domain.interactor.GetRemotePokemonUserCase
import com.android.pokemon.presentation.util.BaseViewModel
import com.android.pokemon.presentation.util.Failure
import com.android.pokemon.presentation.util.Resource
import javax.inject.Inject

class PokedexViewModel @Inject constructor(
    private val getRemotePokemonUserCase: GetRemotePokemonUserCase,
    private val getLocalPokemonUserCase: GetLocalPokemonUserCase
) :
    BaseViewModel() {
    var liveGetPokemons: MutableLiveData<Resource<List<PokemonEntity>>> = MutableLiveData()

    fun getRemotePokemon() = with(liveGetPokemons) {
        postLoading()

        fun onSuccess(data: GetPokemonsResponse) {
            postSuccess(data.pokemon_species)
        }

        fun onFailure(failure: Failure) {
            postFailure(failure)
        }

        getRemotePokemonUserCase.invoke(GetPokemonRequest(), ::onSuccess, ::onFailure)
    }

    fun getLocalPokemon() = with(liveGetPokemons) {
        fun onSuccess(data: List<PokemonEntity>) {
            if (data.isNotEmpty()) {
                postSuccess(data)
            } else {
                getRemotePokemon()
            }
        }

        fun onFailure(failure: Failure) {
            getRemotePokemon()
        }

        getLocalPokemonUserCase.invoke(GetPokemonRequest(), ::onSuccess, ::onFailure)
    }


    fun validatePersistence() {
        if (liveGetPokemons.value == null) {
            getLocalPokemon()
        }
    }
}