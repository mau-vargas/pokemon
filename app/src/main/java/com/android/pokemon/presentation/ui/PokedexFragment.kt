package com.android.pokemon.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.android.pokemon.R
import com.android.pokemon.databinding.FragmentPokedexBinding
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.presentation.appComponent
import com.android.pokemon.presentation.util.BaseFragment
import com.android.pokemon.presentation.util.Failure
import com.android.pokemon.presentation.util.look

class PokedexFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_pokedex

    private lateinit var binding: FragmentPokedexBinding

    private val viewModel: PokedexViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentPokedexBinding
        appComponent().inject(this@PokedexFragment)

        //savedInstanceStateValue = savedInstanceState
        //toolbar = binding.includeToolbar.toolbar

        //setUpToolBar(toolbar, R.string.create_counter_title)
        initLiveData()
    }

    private fun initLiveData() {
        viewModel.liveGetPokemons.look(
            this@PokedexFragment,
            ::showProgress,
            ::handleSuccess,
            ::handleFailure
        )
        viewModel.getPokemon()
    }



    private fun handleSuccess(getPokemonsResponse: GetPokemonsResponse) {
       // toolbar.toolbar_title.text = ""
       // hideProgress()
       // showItems(counterResponse)
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.Error -> {
             //   hideProgress()
            }
        }
    }

}